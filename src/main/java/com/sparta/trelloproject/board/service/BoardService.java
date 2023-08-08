package com.sparta.trelloproject.board.service;


import com.sparta.trelloproject.board.dto.BoardListResponseDto;
import com.sparta.trelloproject.board.dto.BoardRequestDto;
import com.sparta.trelloproject.board.dto.BoardResponseDto;
import com.sparta.trelloproject.board.entity.Board;
import com.sparta.trelloproject.board.repository.BoardRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

  private final BoardRepository boardRepository;

  // 보드 생성
  @Transactional
  public void createBoard(UserDetails userDetails, BoardRequestDto boardRequestDto) {

    Board board = Board.builder()
        .boardName(boardRequestDto.getBoardName())
        .description(boardRequestDto.getDescription())
        .color(boardRequestDto.getColor())
        .build();

    boardRepository.save(board);
  }

  // 보드 전체조회
  public void getBoards() {

    BoardListResponseDto boardListResponseDto = new BoardListResponseDto(boardRepository.findAll());

  }

  // 보드 단건조회
  public BoardResponseDto getBoard(Long boardId) {

    return new BoardResponseDto(boardRepository.findById(boardId).orElseThrow(() ->
        new IllegalArgumentException("보드를 찾을 수 없습니다.")));

  }

  // 보드 수정
  public void updateBoard(UserDetails userDetails, Long boardId, BoardRequestDto boardRequestDto) {
    Board board = boardRepository.findById(boardId).orElseThrow(() ->
        new IllegalArgumentException("보드를 찾을 수 없습니다."));

    board.update(boardRequestDto.getBoardName(),
        boardRequestDto.getDescription(),
        boardRequestDto.getColor());

    boardRepository.save(board);
  }

  // 보드 삭제
  public void deleteBoard(UserDetails userDetails, Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(() ->
        new IllegalArgumentException("보드를 찾을 수 없습니다."));

    boardRepository.delete(board);
  }
}
