package com.sparta.trelloproject.board.dto;

import com.sparta.trelloproject.column.dto.ColumnNameResponseDto;
import com.sparta.trelloproject.column.dto.ColumnResponseDto;
import com.sparta.trelloproject.common.color.ColorEnum;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardResponseDto {

    private Long boardId;
    private String boardName;
    private String description;
    private ColorEnum color;
    private String ownerUser;
    private List<CollaboraterResponseDto> collaboraters;
    private List<ColumnNameResponseDto> columns;

  @Builder
  public BoardResponseDto(Long boardId, String boardName, String description, ColorEnum color, String ownerUser,
                          List<CollaboraterResponseDto> collaboraters,
                          List<ColumnNameResponseDto> columnNames) {
    this.boardId = boardId;
    this.boardName = boardName;
    this.description = description;
    this.color = color;
    this.ownerUser = ownerUser;
    this.collaboraters = collaboraters;
    this.columns = columnNames;
  }

}
