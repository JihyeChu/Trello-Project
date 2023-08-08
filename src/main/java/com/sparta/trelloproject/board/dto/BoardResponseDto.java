package com.sparta.trelloproject.board.dto;

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

  @Builder
  public BoardResponseDto(String boardName, String description, String color, String ownerUser, List<CollaboraterResponseDto> collaboraters) {
    this.boardName = boardName;
    this.description = description;
    this.color = color;
    this.ownerUser = ownerUser;
    this.collaboraters = collaboraters;
  }

}
