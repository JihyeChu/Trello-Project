package com.sparta.trelloproject.column.repository;

import com.sparta.trelloproject.board.entity.Board;
import com.sparta.trelloproject.column.entity.Column;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {
    Optional<ColumnEntity> findByBoardIdAndId(Long boardId, Long ColumnId);

    List<ColumnEntity> findAllByBoard(BoardEntity board);

    List<ColumnEntity> findAllByBoardIdOrderByPositionAsc(Long boardId);
}
