package com.sparta.trelloproject.board.dto;

import com.sparta.trelloproject.board.entity.Board;
import java.util.List;
import java.util.stream.Collectors;

public class BoardListResponseDto {
  private List<BoardResponseDto> boardResponseDtoList;

  public BoardListResponseDto(List<Board> boardList) {
    this.boardResponseDtoList = boardList.stream()
        .map(BoardResponseDto::new)
        .collect(Collectors.toList());
  }
}
