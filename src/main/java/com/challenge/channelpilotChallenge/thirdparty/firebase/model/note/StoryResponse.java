package com.challenge.channelpilotChallenge.thirdparty.firebase.model.note;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class StoryResponse extends ItemResponse {
    private Integer descendants;
    private Integer score;
    private String title;
    private String url;
}
