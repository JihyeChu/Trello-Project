package com.sparta.trelloproject.comment.dto;

import com.sparta.trelloproject.comment.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long commentId;
    private String userName;
    private String comment;

    public CommentResponseDto(CommentEntity comment) {
        this.commentId = comment.getId();
        this.userName = comment.getUser().getUserName();
        this.comment = comment.getComment();
    }
}