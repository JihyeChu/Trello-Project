package com.sparta.trelloproject.board.dto;

import com.sparta.trelloproject.board.entity.BoardUser;
import lombok.Getter;

@Getter
public class CollaboraterResponseDto {
    private String collaboraterName;

    public CollaboraterResponseDto(BoardUser boardUser) {
        this.collaboraterName = boardUser.getCollaborateUser().getUserName();
    }
}
