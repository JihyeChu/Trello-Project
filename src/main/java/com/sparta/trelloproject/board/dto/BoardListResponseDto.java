package com.sparta.trelloproject.board.dto;

import com.sparta.trelloproject.board.entity.Board;
import com.sparta.trelloproject.common.color.ColorEnum;
import lombok.Getter;

@Getter
public class BoardListResponseDto {
  private String boardName;
  private String description;
  private ColorEnum color;

  public BoardListResponseDto(Board board) {
    this.boardName = board.getBoardName();
    this.description = board.getDescription();
    this.color = board.getColor();
  }
}
