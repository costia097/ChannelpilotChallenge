package com.challenge.channelpilotChallenge.thirdparty.firebase.model.note;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ItemResponseType {
    @JsonProperty("story")
    STORY,
    @JsonProperty("comment")
    COMMENT;
}
