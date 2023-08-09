package com.sparta.trelloproject.board.repository;

import com.sparta.trelloproject.board.entity.Board;
import com.sparta.trelloproject.board.entity.BoardUser;
import com.sparta.trelloproject.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {

  List<BoardUser> findAllByCollaborateUser(User user);
  List<BoardUser> findAllByOwnerUserAndCollaborateUserAndBoard(User ownerUser, User collaboraterUser, Board board);
}
