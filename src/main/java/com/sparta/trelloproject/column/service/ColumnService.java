package com.sparta.trelloproject.column.service;

import com.sparta.trelloproject.board.entity.Board;
import com.sparta.trelloproject.board.repository.BoardRepository;
import com.sparta.trelloproject.board.repository.BoardUserRepository;
import com.sparta.trelloproject.column.dto.ColumnRequestDto;
import com.sparta.trelloproject.column.dto.ColumnResponseDto;
import com.sparta.trelloproject.column.entity.Column;
import com.sparta.trelloproject.column.repository.ColumnRepository;
import com.sparta.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final BoardUserRepository boardUserRepository;

    @Transactional
    public void createColumn(User user, Long boardId, ColumnRequestDto requestDto) {
        Board board = findBoard(boardId);

        // 보드생성자, 콜라보레이터만 생성가능
        if (checkOwnerCollaborater(user, board)) {
            throw new IllegalArgumentException("컬럼 생성 권한이 없습니다.");
        }

        Column column = new Column(requestDto.getColumnName(), board, user);

        columnRepository.save(column);
    }

    @Transactional(readOnly = true)
    public List<ColumnResponseDto> getColumn(User user, Long boardId) {
        Board board = findBoard(boardId);

        if (checkOwnerCollaborater(user, board)) {
            throw new IllegalArgumentException("컬럼 조회 권한이 없습니다.");
        }
        return columnRepository.findAllByBoard(board).stream().map(ColumnResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateColumn(User user, ColumnRequestDto requestDto, Long boardId, Long columnId) {
        Board board = findBoard(boardId);
        Column column = findColumn(boardId, columnId);

        if (checkOwnerCollaborater(user, board)) {
            throw new IllegalArgumentException("컬럼 수정 권한이 없습니다.");
        }

        column.update(requestDto);
    }

    @Transactional
    public void deleteColumn(User user, Long boardId, Long columnId) {
        Board board = findBoard(boardId);
        Column column = findColumn(boardId, columnId);

        if (checkOwnerCollaborater(user, board)) {
            throw new IllegalArgumentException("컬럼 수정 권한이 없습니다.");
        }

        columnRepository.delete(column);
    }

    // 컬럼 변경 권한 체크
    private boolean checkOwnerCollaborater(User user, Board board) {
        boolean result = boardUserRepository.findAllByCollaborateUserAndBoard(user, board).isEmpty()
                && board.getUser().getId() != user.getId(); // 콜라보레이터에 해당유저 없고 보드생성자도 아닐경우 true.
        return result;
    }

    private Board findBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("선택한 Board 가 존재하지 않습니다. boardId : " + boardId));
    }

    private Column findColumn(Long boardId, Long columnId) {
        return columnRepository.findByBoardIdAndId(boardId, columnId).orElseThrow(
                () -> new IllegalArgumentException("선택한 Column 이 존재하지 않습니다. boardId : " + boardId + ", columnId : " + columnId));
    }
}
