package com.challenge.channelpilotChallenge.domain.service;

import com.challenge.channelpilotChallenge.domain.converter.StoryConverter;
import com.challenge.channelpilotChallenge.domain.entity.Story;
import com.challenge.channelpilotChallenge.thirdparty.firebase.api.FirebaseApi;
import com.challenge.channelpilotChallenge.thirdparty.firebase.model.note.CommentResponse;
import com.challenge.channelpilotChallenge.thirdparty.firebase.model.note.ItemResponse;
import com.challenge.channelpilotChallenge.thirdparty.firebase.model.note.ItemResponseType;
import com.challenge.channelpilotChallenge.thirdparty.firebase.model.note.StoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final static int START_ID = 10_001;

    private final FirebaseApi firebaseApi;
    private final StoryConverter storyConverter;
    private final StoryService storyService;
    private final ExecutorService fixedThreadPool;

    @Transactional
    public void consumeItems(int limit) throws InterruptedException {
        int endId = START_ID + limit;

        List<Integer> idsToRetrieve = evaluateIdsToRetrieve(endId);

        List<Callable<ItemResponse>> tasks = idsToRetrieve.stream()
                .map(this::createRetrieveNoteTask)
                .collect(Collectors.toList());

        List<Future<ItemResponse>> futures = fixedThreadPool.invokeAll(tasks);

        List<Story> stories = processItemFutures(futures);

        storyService.storeStories(stories);
    }


    private List<Story> processItemFutures(List<Future<ItemResponse>> futures) {
        List<ItemResponse> items = futures.stream()
                .map(this::extractResponseFromFutureItem)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<Long, CommentResponse> allCommentsPerId = items.stream()
                .filter(itemResponse -> ItemResponseType.COMMENT.equals(itemResponse.getType()))
                .map(itemResponse -> (CommentResponse) itemResponse)
                .collect(Collectors.toMap(ItemResponse::getId, Function.identity()));

        return items.stream()
                .filter(itemResponse -> ItemResponseType.STORY.equals(itemResponse.getType()))
                .map(itemResponse -> (StoryResponse) itemResponse)
                .map(storyResponse -> storyConverter.toEntity(storyResponse, allCommentsPerId))
                .collect(Collectors.toList());
    }

    private List<Integer> evaluateIdsToRetrieve(int endId) {
        return IntStream.range(START_ID, endId).boxed().collect(Collectors.toList());
    }

    private Callable<ItemResponse> createRetrieveNoteTask(int noteId) {
        return () -> firebaseApi.retrieveItemWithId(noteId);
    }

    private ItemResponse extractResponseFromFutureItem(Future<ItemResponse> future) {
        ItemResponse itemResponse = null;
        try {
            itemResponse = future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Failed to get future: ", e);
        }

        return itemResponse;
    }
}
