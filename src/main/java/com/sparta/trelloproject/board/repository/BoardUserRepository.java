package com.sparta.trelloproject.board.repository;

import com.sparta.trelloproject.board.entity.Board;
import com.sparta.trelloproject.board.entity.BoardUser;
import com.sparta.trelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {

    List<BoardUser> findAllByCollaborateUser(User user);

    List<BoardUser> findAllByCollaborateUserAndBoard(User collaboraterUser, Board board);

    List<BoardUser> findAllByOwnerUserAndCollaborateUserAndBoard(User ownerUser, User collaboraterUser, Board board);
}
