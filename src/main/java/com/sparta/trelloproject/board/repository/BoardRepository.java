package com.sparta.trelloproject.board.repository;

import com.sparta.trelloproject.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findAllByUserId(Long id);
}
