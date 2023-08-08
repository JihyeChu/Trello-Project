package com.sparta.trelloproject.board.controller;

import com.sparta.trelloproject.board.dto.BoardRequestDto;
import com.sparta.trelloproject.board.dto.BoardResponseDto;
import com.sparta.trelloproject.board.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
  private final BoardService boardService;

  // 보드 생성
  @PostMapping("/boards")
  public void createBoard(@AuthenticationPrincipal UserDetails userDetails, @RequestBody BoardRequestDto boardRequestDto) {
    boardService.createBoard(userDetails, boardRequestDto);
  }

  // 보드 전체 조회
  @GetMapping("/boards")
  public void getBoards(@AuthenticationPrincipal UserDetails userDetails) {
    boardService.getBoards();
  }

  // 보드 단건 조회
  @GetMapping("/boards/{boardId}")
  public BoardResponseDto getBoard(@PathVariable Long boardId) {
    return boardService.getBoard(boardId);
  }

  // 보드 수정
  @PutMapping("/boards/{boardId}")
  public void updateBoard(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto) {
    boardService.updateBoard(userDetails,boardId, boardRequestDto);
  }

  // 보드 삭제
  @DeleteMapping("/boards/{boardId}")
  public void deleteBoard(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long boardId) {
    boardService.deleteBoard(userDetails, boardId);
  }
}
