package com.sparta.trelloproject.board.dto;

import com.sparta.trelloproject.common.color.ColorEnum;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String boardName;
    private String description;
    private ColorEnum color;

}
