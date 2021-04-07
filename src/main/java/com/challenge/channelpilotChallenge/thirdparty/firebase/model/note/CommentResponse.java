package com.challenge.channelpilotChallenge.thirdparty.firebase.model.note;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CommentResponse extends ItemResponse {
    private String text;
    private String by;
}
