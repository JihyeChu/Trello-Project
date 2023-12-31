package com.sparta.trelloproject.board.repository;

import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.board.entity.BoardUserEntity;
import com.sparta.trelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardUserRepository extends JpaRepository<BoardUserEntity, Long> {

    List<BoardUserEntity> findAllByCollaborateUser(User user);

    List<BoardUserEntity> findAllByCollaborateUserAndBoard(User collaboraterUser, BoardEntity board);

    List<BoardUserEntity> findAllByOwnerUserAndCollaborateUserAndBoard(User ownerUser, User collaboraterUser, BoardEntity board);

}
