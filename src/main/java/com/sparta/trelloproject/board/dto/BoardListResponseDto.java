package com.sparta.trelloproject.board.dto;

import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.common.color.ColorEnum;
import lombok.Getter;

@Getter
public class BoardListResponseDto {
    private String boardName;
    private String description;
    private ColorEnum color;

    public BoardListResponseDto(BoardEntity board) {
        this.boardName = board.getBoardName();
        this.description = board.getDescription();
        this.color = board.getColor();
    }
}
