package com.sparta.trelloproject.board.dto;

import com.sparta.trelloproject.board.entity.BoardUserEntity;
import lombok.Getter;

@Getter
public class CollaboraterResponseDto {
    private String collaboraterName;

    public CollaboraterResponseDto(BoardUserEntity boardUser) {
        this.collaboraterName = boardUser.getCollaborateUser().getUserName();
    }
}
