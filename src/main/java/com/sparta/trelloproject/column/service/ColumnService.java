//package com.sparta.trelloproject.column.service;
//
//import com.sparta.trelloproject.board.entity.Board;
//import com.sparta.trelloproject.board.repository.BoardRepository;
//import com.sparta.trelloproject.column.dto.ColumnRequestDto;
//import com.sparta.trelloproject.column.dto.ColumnResponseDto;
//import com.sparta.trelloproject.column.entity.Column;
//import com.sparta.trelloproject.column.repository.ColumnRepository;
//import com.sparta.trelloproject.user.entity.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.concurrent.RejectedExecutionException;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class ColumnService {
//    private final ColumnRepository columnRepository;
//    private final BoardRepository boardRepository;
//
//    @Transactional
//    public void createColumn(User user, ColumnRequestDto requestDto, Long boardId) {
//        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("선택한 Board 가 존재하지 않습니다. boardId : " + boardId));
//
//        Column column = new Column(requestDto.getColumnName(), board, user);
//
//        columnRepository.save(column);
//    }
//
//    @Transactional(readOnly = true)
//    public List<ColumnResponseDto> getColumn() {
//        return columnRepository.findAll().stream().map(ColumnResponseDto::new).collect(Collectors.toList());
//    }
//
//    @Transactional
//    public void updateColumn(User user, ColumnRequestDto requestDto, Long boardId, Long columnId) {
//        Column column = columnRepository.findByBoardIdAndColumnId(boardId, columnId).orElseThrow(() -> new NullPointerException("선택한 Column 이 존재하지 않습니다. boardId : " + boardId + "columnId : " + columnId))
//
//        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || column.getUser().equals(user))) {
//            throw new RejectedExecutionException("작성자, 관리자만 수정할 수 있습니다.");
//        }
//
//        column.update(requestDto);
//    }
//
//    @Transactional
//    public void deleteColumn(User user, Long boardId, Long columnId) {
//        Column column = columnRepository.findByBoardIdAndColumnId(boardId, columnId).orElseThrow(() -> new NullPointerException("선택한 Column 이 존재하지 않습니다. boardId : " + boardId + "columnId : " + columnId));
//
//        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || column.getUser().equals(user))) {
//            throw new RejectedExecutionException("작성자, 관리자만 삭제할 수 있습니다.");
//        }
//
//        columnRepository.delete(column);
//    }
//}
