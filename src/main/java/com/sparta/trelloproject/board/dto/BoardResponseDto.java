package com.sparta.trelloproject.board.dto;

import com.sparta.trelloproject.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {
  private String boardName;
  private String description;
  private String color;

  public BoardResponseDto(Board board) {
    this.boardName = board.getBoardName();
    this.description = board.getDescription();
    this.color = board.getColor();
  }
}
