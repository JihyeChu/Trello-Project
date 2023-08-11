package com.sparta.trelloproject.comment.dto;

import com.sparta.trelloproject.comment.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {

    private String comment;


    public CommentResponseDto(CommentEntity comment){
        this.comment = comment.getComment();
    }

}