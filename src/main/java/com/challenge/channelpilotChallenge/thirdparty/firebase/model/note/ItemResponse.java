package com.challenge.channelpilotChallenge.thirdparty.firebase.model.note;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CommentResponse.class, name = "comment"),
        @JsonSubTypes.Type(value = StoryResponse.class, name = "story")
})
public abstract class ItemResponse {
    private Long id;
    private Timestamp time;
    private ItemResponseType type;
    private Long parent;
    private List<Long> kids;
}
