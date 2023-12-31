package com.sparta.trelloproject.column.service;

import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.board.service.BoardService;
import com.sparta.trelloproject.column.dto.ColumnMoveDto;
import com.sparta.trelloproject.column.dto.ColumnRequestDto;
import com.sparta.trelloproject.column.dto.ColumnResponseDto;
import com.sparta.trelloproject.column.entity.ColumnEntity;
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
    private final BoardService boardService;

    @Transactional
    public void createColumn(User user, Long boardId, ColumnRequestDto requestDto) {
        BoardEntity board = boardService.findBoard(boardId);

        // 보드생성자, 콜라보레이터만 생성가능
        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        // position => 1024 씩 증가, 이동 후 생성 시 position 이 가장 큰 값 + 1024
        List<ColumnEntity> sortedColumnList = columnRepository.findAllByBoardIdOrderByPositionAsc(board.getId());
        int position = (sortedColumnList.size() != 0) ? sortedColumnList.get((sortedColumnList.size() - 1)).getPosition() + 1024 : 1024;

        ColumnEntity column = new ColumnEntity(requestDto.getColumnName(), board, user, position);

        columnRepository.save(column);
    }

    @Transactional(readOnly = true)
    public List<ColumnResponseDto> getColumn(User user, Long boardId) {
        BoardEntity board = boardService.findBoard(boardId);

        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        return columnRepository.findAllByBoardIdOrderByPositionAsc(boardId).stream()
                .map(ColumnResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void updateColumn(User user, ColumnRequestDto requestDto, Long boardId, Long columnId) {
        BoardEntity board = boardService.findBoard(boardId);
        ColumnEntity column = findColumn(boardId, columnId);

        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        column.update(requestDto);
    }


    @Transactional
    public void deleteColumn(User user, Long boardId, Long columnId) {
        BoardEntity board = boardService.findBoard(boardId);
        ColumnEntity column = findColumn(boardId, columnId);

        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        columnRepository.delete(column);
    }


    @Transactional
    public List<ColumnResponseDto> moveColumn(User user, Long boardId, ColumnMoveDto moveDto) {
        BoardEntity board = boardService.findBoard(boardId);

        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        // 이동시킬 컬럼
        ColumnEntity currentColumn = findColumn(boardId, (long) moveDto.getSelectColumnId());
        // selectPosition = 컬럼을 이동시킬 위치
        ColumnEntity selectColumn = board.getColumns().get(moveDto.getSelectIndex() - 1);
        int selectPosition = selectColumn.getPosition();

        // 위치 순으로 정렬된 컬럼
        List<ColumnEntity> sortedColumnList = columnRepository.findAllByBoardIdOrderByPositionAsc(boardId);

        // selectColumn 앞 혹은 뒤 position
        int aroundPosition = getAroundPosition(moveDto, sortedColumnList);

        // 이동
        move(currentColumn, selectPosition, aroundPosition);

        return columnRepository.findAllByBoardIdOrderByPositionAsc(boardId).stream()
                .map(ColumnResponseDto::new)
                .collect(Collectors.toList());
    }


    public ColumnEntity findColumn(Long boardId, Long columnId) {
        return columnRepository.findByBoardIdAndId(boardId, columnId).orElseThrow(
                () -> new IllegalArgumentException("선택한 Column 이 존재하지 않습니다. boardId : " + boardId + ", columnId : " + columnId));
    }

    private void move(ColumnEntity currentColumn, int selectPosition, int aroundPosition) {
        int movePosition = (selectPosition + aroundPosition) / 2;
        currentColumn.moveColumn(movePosition);
    }

    private int getAroundPosition(ColumnMoveDto moveDto, List<ColumnEntity> sortedColumnList) {
        int aroundPosition;
        if (moveDto.getSelectIndex() >= sortedColumnList.size() - 1) {
            aroundPosition = sortedColumnList.get(sortedColumnList.size() - 1).getPosition() + 1024;
        } else if (moveDto.getSelectIndex() == 1) {
            int nextPosition = sortedColumnList.get(0).getPosition();
            aroundPosition = Math.min(nextPosition - 1024, 0);
        } else {
            int prevPosition = sortedColumnList.get(moveDto.getSelectIndex() - 1).getPosition();
            int nextPosition = sortedColumnList.get(moveDto.getSelectIndex() + 1).getPosition();
            aroundPosition = (prevPosition + nextPosition) / 2;
        }
        return aroundPosition;
    }
}
