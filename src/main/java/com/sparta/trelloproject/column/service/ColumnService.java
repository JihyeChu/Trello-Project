package com.sparta.trelloproject.column.service;

import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.board.repository.BoardRepository;
import com.sparta.trelloproject.board.repository.BoardUserRepository;
import com.sparta.trelloproject.column.dto.ColumnRequestDto;
import com.sparta.trelloproject.column.dto.ColumnResponseDto;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import com.sparta.trelloproject.column.repository.ColumnRepository;
import com.sparta.trelloproject.user.entity.User;
import com.sparta.trelloproject.user.entity.UserRoleEnum;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColumnService {

  private final ColumnRepository columnRepository;
  private final BoardRepository boardRepository;
  private final BoardUserRepository boardUserRepository;

  @Transactional
  public void createColumn(User user, Long boardId, ColumnRequestDto requestDto) {
    BoardEntity board = boardRepository.findById(boardId)
        .orElseThrow(() -> new NullPointerException("선택한 Board 가 존재하지 않습니다. boardId : " + boardId));

    // 보드생성자, 콜라보레이터만 생성가능
    if (checkOwnerCollaborater(user, board)) {
      throw new IllegalArgumentException("컬럼생성 권한이 없습니다.");
    }

    ColumnEntity column = new ColumnEntity(requestDto.getColumnName(), board, user);

    columnRepository.save(column);
  }

  @Transactional(readOnly = true)
  public List<ColumnResponseDto> getColumn() {
    return columnRepository.findAll().stream().map(ColumnResponseDto::new)
        .collect(Collectors.toList());
  }

  @Transactional
  public void updateColumn(User user, ColumnRequestDto requestDto, Long boardId, Long columnId) {
    ColumnEntity column = columnRepository.findByBoardIdAndId(boardId, columnId).orElseThrow(
        () -> new NullPointerException(
            "선택한 Column 이 존재하지 않습니다. boardId : " + boardId + "columnId : " + columnId));

    if (!(user.getRole().equals(UserRoleEnum.ADMIN) || column.getUser().getId().equals(user.getId()))) {
      throw new RejectedExecutionException("작성자, 관리자만 수정할 수 있습니다.");
    }

    column.update(requestDto);
  }

  @Transactional
  public void deleteColumn(User user, Long boardId, Long columnId) {
    ColumnEntity column = columnRepository.findByBoardIdAndId(boardId, columnId).orElseThrow(
        () -> new NullPointerException(
            "선택한 Column 이 존재하지 않습니다. boardId : " + boardId + "columnId : " + columnId));

    if (!(user.getRole().equals(UserRoleEnum.ADMIN) || column.getUser().getId().equals(user.getId()))) {
      throw new RejectedExecutionException("작성자, 관리자만 삭제할 수 있습니다.");
    }

    columnRepository.delete(column);
  }

  // 컬럼 변경 권한 체크
  private boolean checkOwnerCollaborater(User user, BoardEntity board) {
    boolean result = boardUserRepository.findAllByCollaborateUserAndBoard(user, board).isEmpty()
        && board.getUser().getId() != user.getId(); // 콜라보레이터에 해당유저 없고 보드생성자도 아닐경우 true.
      return result;
    }

  }
