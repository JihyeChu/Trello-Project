package com.sparta.trelloproject.board.repository;

import com.sparta.trelloproject.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllById(Long id);
}
