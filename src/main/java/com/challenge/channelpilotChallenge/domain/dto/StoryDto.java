package com.challenge.channelpilotChallenge.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoryDto {
    private Long id;
    private String title;
    private Integer descendants;
    private Integer score;
    private String url;

    private List<CommentDto> comments;
}
