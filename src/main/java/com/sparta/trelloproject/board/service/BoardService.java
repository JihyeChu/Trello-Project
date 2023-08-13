package com.sparta.trelloproject.board.service;


import com.sparta.trelloproject.board.dto.BoardListResponseDto;
import com.sparta.trelloproject.board.dto.BoardRequestDto;
import com.sparta.trelloproject.board.dto.BoardResponseDto;
import com.sparta.trelloproject.board.dto.CollaboraterResponseDto;
import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.board.entity.BoardUserEntity;
import com.sparta.trelloproject.board.repository.BoardRepository;
import com.sparta.trelloproject.board.repository.BoardUserRepository;
import com.sparta.trelloproject.column.dto.ColumnNameResponseDto;
import com.sparta.trelloproject.column.repository.ColumnRepository;
import com.sparta.trelloproject.common.security.UserDetailsImpl;
import com.sparta.trelloproject.user.entity.User;
import com.sparta.trelloproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardUserRepository boardUserRepository;
    private final ColumnRepository columnRepository;

    // 보드 생성
    @Transactional
    public void createBoard(UserDetailsImpl userDetails, BoardRequestDto boardRequestDto)
            throws IOException {

        BoardEntity board = BoardEntity.builder()
                .boardName(boardRequestDto.getBoardName())
                .description(boardRequestDto.getDescription())
                .color(boardRequestDto.getColor())
                .user(userDetails.getUser())
                .build();

        boardRepository.save(board);
    }

    // 사용자가 속한 보드 전체조회
    @Transactional(readOnly = true)
    public List<BoardListResponseDto> getBoards(User user) {
        List<BoardEntity> allMyBoards = new ArrayList<>();

        // 본인이 콜라보레이터 초대된 보드들 가져오기
        List<BoardEntity> collaborateBoards = boardUserRepository.findAllByCollaborateUser(
                        user).stream()
                .map(BoardUserEntity::getBoard)
                .collect(Collectors.toList());
        for (BoardEntity collaborateBoard : collaborateBoards) {
            allMyBoards.add(collaborateBoard);
        }

        // 본인이 만든 보드 가져오기
        List<BoardEntity> myBoards = boardRepository.findAllByUserId(user.getId());
        for (BoardEntity myBoard : myBoards) {
            allMyBoards.add(myBoard);
        }

        return allMyBoards.stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
    }

    // 보드 단건조회
    // + 해당 보드의 생성자,콜라보레이터 함께 조회
    @Transactional(readOnly = true)
    public BoardResponseDto getBoard(User user, Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("보드를 찾을 수 없습니다."));

        if (checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("보드 생성자 / 콜라보레이터 가 아닌 사용자는 조회할 수 없습니다.");
        }

        return BoardResponseDto.builder()
                .boardId(board.getId())
                .boardName(board.getBoardName())
                .description(board.getDescription())
                .color(board.getColor())
                .ownerUser(board.getUser().getUserName())
                .collaboraters(board.getBoardUsers().stream()
                        .map(CollaboraterResponseDto::new)
                        .collect(Collectors.toList()))
                .columnNames(columnRepository.findAllByBoardIdOrderByPositionAsc(board.getId()).stream()
                        .map(ColumnNameResponseDto::new)
                        .collect(Collectors.toList()))
                .build();
    }

    // 보드 수정
    @Transactional
    public void updateBoard(UserDetailsImpl userDetails, Long boardId,
                            BoardRequestDto boardRequestDto) throws IOException {
        BoardEntity board = findBoard(boardId);

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
        BoardEntity board = findBoard(boardId);

        // 보드 생성자만 삭제 가능하도록
        if (!board.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("보드 생성자만 삭제할 수 있습니다.");
        }

        boardRepository.delete(board);
    }

    // 보드에 콜라보레이터 초대
    @Transactional
    public void inviteUser(UserDetailsImpl userDetails, Long boardId, Long userId) {

        BoardEntity board = findBoard(boardId);

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

        if (!boardUserRepository
                .findAllByOwnerUserAndCollaborateUserAndBoard(board.getUser(), user, board)
                .isEmpty()) {
            throw new IllegalArgumentException("이미 초대하셨습니다.");
        }

        BoardUserEntity boardUser = new BoardUserEntity(user, board);

        boardUserRepository.save(boardUser);
    }

    // 보드 권한 체크
    public boolean checkOwnerCollaborator(User user, BoardEntity board) {
        return boardUserRepository.findAllByCollaborateUserAndBoard(user, board).isEmpty()
                && board.getUser().getId() != user.getId();
    }

    public BoardEntity findBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("선택한 Board 가 존재하지 않습니다. boardId : " + boardId));
    }
}
