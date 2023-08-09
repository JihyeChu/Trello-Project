package com.sparta.trelloproject.board.dto;

import com.sparta.trelloproject.column.dto.ColumnResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardResponseDto {

  private String boardName;
  private String description;
  private String color;
  private String ownerUser;
  private List<CollaboraterResponseDto> collaboraters;
  private List<ColumnResponseDto> columnList;

  @Builder
  public BoardResponseDto(String boardName, String description, String color, String ownerUser,
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
