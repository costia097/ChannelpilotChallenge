package com.challenge.channelpilotChallenge.domain.converter;

import com.challenge.channelpilotChallenge.domain.dto.CommentDto;
import com.challenge.channelpilotChallenge.domain.dto.StoryDto;
import com.challenge.channelpilotChallenge.domain.entity.Comment;
import com.challenge.channelpilotChallenge.domain.entity.Story;
import com.challenge.channelpilotChallenge.thirdparty.firebase.model.note.CommentResponse;
import com.challenge.channelpilotChallenge.thirdparty.firebase.model.note.StoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StoryConverter {
    private final CommentConverter commentConverter;

    public Story toEntity(StoryResponse storyResponse, Map<Long, CommentResponse> allCommentsPerId) {
        var story = new Story();

        if (storyResponse.getKids() != null && !storyResponse.getKids().isEmpty()) {
            List<Comment> storyComments = storyResponse.getKids()
                    .stream()
                    .map(allCommentsPerId::get)
                    .filter(Objects::nonNull)
                    .map(commentResponse -> commentConverter.toEntity(commentResponse, allCommentsPerId))
                    .collect(Collectors.toList());

            story.setComments(storyComments);
        }

        story.setStoryId(storyResponse.getId());
        story.setTitle(storyResponse.getTitle());
        story.setDescendants(storyResponse.getDescendants());
        story.setScore(storyResponse.getScore());
        story.setUrl(storyResponse.getUrl());

        return story;
    }

    public StoryDto toDtoWithoutComments(Story story) {
        StoryDto storyDto = new StoryDto();

        storyDto.setId(story.getStoryId());
        storyDto.setTitle(story.getTitle());

        return storyDto;
    }

    public StoryDto toDto(Story story) {
        StoryDto storyDto = new StoryDto();

        storyDto.setId(story.getStoryId());
        storyDto.setTitle(story.getTitle());
        storyDto.setDescendants(story.getDescendants());
        storyDto.setScore(story.getScore());
        storyDto.setUrl(story.getUrl());

        if (story.getComments() != null && !story.getComments().isEmpty()) {
            List<CommentDto> commentDtos = story.getComments().stream()
                    .map(commentConverter::toDto)
                    .collect(Collectors.toList());

            storyDto.setComments(commentDtos);
        }

        return storyDto;
    }
}
