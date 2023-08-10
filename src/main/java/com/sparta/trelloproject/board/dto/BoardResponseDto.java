package com.sparta.trelloproject.board.dto;

import com.sparta.trelloproject.column.dto.ColumnResponseDto;
import com.sparta.trelloproject.common.color.ColorEnum;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardResponseDto {

    private String boardName;
    private String description;
    private ColorEnum color;
    private String ownerUser;
    private List<CollaboraterResponseDto> collaboraters;
    private List<ColumnResponseDto> columnList;

    @Builder
    public BoardResponseDto(String boardName, String description, ColorEnum color, String ownerUser,
                            List<CollaboraterResponseDto> collaboraters,
                            List<ColumnResponseDto> columnList) {
        this.boardName = boardName;
        this.description = description;
        this.color = color;
        this.ownerUser = ownerUser;
        this.collaboraters = collaboraters;
        this.columnList = columnList;
    }

}
