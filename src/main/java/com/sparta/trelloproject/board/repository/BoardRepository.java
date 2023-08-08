package com.sparta.trelloproject.board.repository;

import com.sparta.trelloproject.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
