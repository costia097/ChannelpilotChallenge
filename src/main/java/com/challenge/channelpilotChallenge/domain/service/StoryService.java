package com.challenge.channelpilotChallenge.domain.service;

import com.challenge.channelpilotChallenge.domain.converter.StoryConverter;
import com.challenge.channelpilotChallenge.domain.dto.StoryDto;
import com.challenge.channelpilotChallenge.domain.entity.Story;
import com.challenge.channelpilotChallenge.domain.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoryService {
    private final StoryRepository storyRepository;
    private final StoryConverter storyConverter;

    @Transactional
    public void storeStories(List<Story> stories) {
        storyRepository.saveAll(stories);
    }

    @Transactional(readOnly = true)
    public List<StoryDto> retrieveAllStories() {
        List<Story> stories = storyRepository.findAll();

        return stories.stream()
                .map(storyConverter::toDtoWithoutComments)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StoryDto> retrieveAllByStoryId(Long storyId) {
        List<Story> storiesWithComments = storyRepository.findAllByStoryIdWithComments(storyId);

        return storiesWithComments
                .stream()
                .map(storyConverter::toDto)
                .collect(Collectors.toList());
    }
}
