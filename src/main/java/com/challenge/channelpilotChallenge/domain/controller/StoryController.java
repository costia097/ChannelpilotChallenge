package com.challenge.channelpilotChallenge.domain.controller;

import com.challenge.channelpilotChallenge.domain.dto.StoryDto;
import com.challenge.channelpilotChallenge.domain.service.StoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class StoryController {
    private final StoryService storyService;

    @GetMapping("/list")
    public List<StoryDto> retrieveAllStories() {
        return storyService.retrieveAllStories();
    }

    @GetMapping("/{storyId}/list")
    public List<StoryDto> retrieveAllByStoryId(@PathVariable Long storyId) {
        return storyService.retrieveAllByStoryId(storyId);
    }
}
