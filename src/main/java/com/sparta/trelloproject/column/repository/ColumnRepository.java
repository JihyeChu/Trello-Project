package com.sparta.trelloproject.column.repository;

import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {
    Optional<ColumnEntity> findByBoardIdAndId(Long boardId, Long ColumnId);

    List<ColumnEntity> findAllByBoard(BoardEntity board);
}
