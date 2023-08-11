package com.sparta.trelloproject.board.repository;

import com.sparta.trelloproject.board.entity.Board;
import com.sparta.trelloproject.board.entity.BoardUser;
import com.sparta.trelloproject.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardUserRepository extends JpaRepository<BoardUserEntity, Long> {

  List<BoardUserEntity> findAllByCollaborateUser(User user);
  List<BoardUserEntity> findAllByCollaborateUserAndBoard(User collaboraterUser, BoardEntity board);
  List<BoardUserEntity> findAllByOwnerUserAndCollaborateUserAndBoard(User ownerUser, User collaboraterUser, BoardEntity board);

}
