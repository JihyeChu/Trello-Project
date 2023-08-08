package com.sparta.trelloproject.column.repository;

import com.sparta.trelloproject.column.entity.Column;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColumnRepository extends JpaRepository<Column, Long> {
    Optional<Column> findByBoardIdAndColumnId(Long boardId, Long ColumnId);
}
