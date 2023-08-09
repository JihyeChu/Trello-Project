package com.sparta.trelloproject.column.repository;

import com.sparta.trelloproject.board.entity.Board;
import com.sparta.trelloproject.column.entity.Column;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColumnRepository extends JpaRepository<Column, Long> {
    Optional<Column> findByBoardIdAndId(Long boardId, Long ColumnId);

    List<Column> findAllByBoard(Board board);
}
