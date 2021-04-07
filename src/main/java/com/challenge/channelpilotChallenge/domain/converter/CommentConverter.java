package com.challenge.channelpilotChallenge.domain.converter;

import com.challenge.channelpilotChallenge.domain.dto.CommentDto;
import com.challenge.channelpilotChallenge.domain.entity.Comment;
import com.challenge.channelpilotChallenge.thirdparty.firebase.model.note.CommentResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CommentConverter {

    public Comment toEntity(CommentResponse response, Map<Long, CommentResponse> allCommentsPerId) {
        var comment = new Comment();

        comment.setCommentId(response.getId());

        comment.setText(response.getText());
        comment.setBy(response.getBy());

        comment.setTime(response.getTime().toLocalDateTime());

        if (response.getKids() != null && !response.getKids().isEmpty()) {
            List<Comment> kids = response.getKids().stream()
                    .map(allCommentsPerId::get)
                    .filter(Objects::nonNull)
                    .map(commentResponse -> toEntity(commentResponse, allCommentsPerId))
                    .collect(Collectors.toList());

            comment.setKids(kids);
        }

        return comment;
    }

    public CommentDto toDto(Comment comment) {
        var commentDto = new CommentDto();

        commentDto.setCommentId(comment.getCommentId());
        commentDto.setTime(comment.getTime());
        commentDto.setText(comment.getText());
        commentDto.setBy(comment.getBy());

        if (comment.getKids() != null && !comment.getKids().isEmpty()) {
            List<CommentDto> kidDtos = comment.getKids().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            commentDto.setKids(kidDtos);
        }

        return commentDto;
    }
}
