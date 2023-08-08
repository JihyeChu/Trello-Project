package com.sparta.trelloproject.board.service;


import com.sparta.trelloproject.board.dto.BoardListResponseDto;
import com.sparta.trelloproject.board.dto.BoardRequestDto;
import com.sparta.trelloproject.board.dto.BoardResponseDto;
import com.sparta.trelloproject.board.dto.CollaboraterResponseDto;
import com.sparta.trelloproject.board.entity.Board;
import com.sparta.trelloproject.board.entity.BoardUser;
import com.sparta.trelloproject.board.repository.BoardRepository;
import com.sparta.trelloproject.board.repository.BoardUserRepository;
import com.sparta.trelloproject.common.security.UserDetailsImpl;
import com.sparta.trelloproject.user.entity.User;
import com.sparta.trelloproject.user.repository.UserRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;
  private final BoardUserRepository boardUserRepository;

  // 보드 생성
  @Transactional
  public void createBoard(UserDetailsImpl userDetails, BoardRequestDto boardRequestDto)
      throws IOException {

    Board board = Board.builder()
        .boardName(boardRequestDto.getBoardName())
        .description(boardRequestDto.getDescription())
        .color(boardRequestDto.getColor())
        .user(userDetails.getUser())
        .build();

    boardRepository.save(board);
  }

  // 사용자가 속한 보드 전체조회
  public List<BoardListResponseDto> getBoards(UserDetailsImpl userDetails) {
    List<Board> allMyBoards = new ArrayList<>();

    // 본인이 콜라보레이터 초대된 보드들 가져오기
    List<Board> collaborateBoards = boardUserRepository.findAllByCollaborateUser(
            userDetails.getUser()).stream()
        .map(BoardUser::getBoard)
        .collect(Collectors.toList());
    for (Board collaborateBoard : collaborateBoards) {
      allMyBoards.add(collaborateBoard);
    }

    // 본인이 만든 보드 가져오기
    List<Board> myBoards = boardRepository.findAllById(userDetails.getUser().getId());
    for (Board myBoard : myBoards) {
      allMyBoards.add(myBoard);
    }

    return allMyBoards.stream()
        .map(BoardListResponseDto::new)
        .collect(Collectors.toList());
  }

  // 보드 단건조회
  // + 해당 보드의 생성자,콜라보레이터 함께 조회
  public BoardResponseDto getBoard(Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(() ->
        new IllegalArgumentException("보드를 찾을 수 없습니다."));

    BoardResponseDto boardResponseDto = BoardResponseDto.builder()
        .boardName(board.getBoardName())
        .description(board.getDescription())
        .color(board.getColor())
        .ownerUser(board.getUser().getUserName())
        .collaboraters(board.getBoardUsers().stream()
            .map(CollaboraterResponseDto::new)
            .collect(Collectors.toList()))
        .build();
    return boardResponseDto;
  }

  // 보드 수정
  @Transactional
  public void updateBoard(UserDetailsImpl userDetails, Long boardId,
      BoardRequestDto boardRequestDto) throws IOException {
    Board board = boardRepository.findById(boardId).orElseThrow(() ->
        new IllegalArgumentException("보드를 찾을 수 없습니다."));

    // 보드 생성자만 수정 가능하도록
    if (!board.getUser().getId().equals(userDetails.getUser().getId())) {
      throw new IllegalArgumentException("보드 생성자만 수정할 수 있습니다.");
    }

    board.update(boardRequestDto.getBoardName(),
        boardRequestDto.getDescription(),
        boardRequestDto.getColor());

    boardRepository.save(board);
  }

  // 보드 삭제
  @Transactional
  public void deleteBoard(UserDetailsImpl userDetails, Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(() ->
        new IllegalArgumentException("보드를 찾을 수 없습니다."));

    // 보드 생성자만 삭제 가능하도록
    if (!board.getUser().getId().equals(userDetails.getUser().getId())) {
      throw new IllegalArgumentException("보드 생성자만 삭제할 수 있습니다.");
    }

    boardRepository.delete(board);
  }

  // 보드에 콜라보레이터 초대
  @Transactional
  public void inviteUser(UserDetailsImpl userDetails, Long boardId, Long userId) {

    Board board = boardRepository.findById(boardId).orElseThrow(() ->
        new IllegalArgumentException("보드를 찾을 수 없습니다."));

    // 보드의 작성자만 초대가능하도록 예외처리
    if (board.getUser().getId() != userDetails.getUser().getId()) {
      throw new IllegalArgumentException("보드 생성자만 초대 가능합니다.");
    }

    // 콜라보레이터 초대할 사용자
    User user = userRepository.findById(userId).orElseThrow(() ->
        new IllegalArgumentException("회원을 찾을 수 없습니다."));

    // 본인을 콜라보레이터로 지정시 예외처리
    if (user.getId().equals(userDetails.getUser().getId())) {
      throw new IllegalArgumentException("본인은 추가할 필요 없습니다.");
    }

    BoardUser boardUser = new BoardUser(user, board);

    boardUserRepository.save(boardUser);

  }
}
