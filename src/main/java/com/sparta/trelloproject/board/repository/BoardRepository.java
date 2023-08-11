package com.sparta.trelloproject.board.repository;

import com.sparta.trelloproject.board.entity.BoardEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
  List<BoardEntity> findAllById(Long id);
}
