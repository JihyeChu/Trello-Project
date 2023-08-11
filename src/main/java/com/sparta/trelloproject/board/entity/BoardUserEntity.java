package com.sparta.trelloproject.board.entity;

import com.sparta.trelloproject.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardUserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_user_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false)
  User ownerUser;

  @ManyToOne
  @JoinColumn(name = "collaborate_user_id", nullable = false)
  User collaborateUser;

  @ManyToOne
  @JoinColumn(name = "board_id", nullable = false)
  BoardEntity board;

  public BoardUserEntity(User collaborateUser, BoardEntity board) {
    this.ownerUser = board.getUser();
    this.collaborateUser = collaborateUser;
    this.board = board;
    board.addBaordUser(this);
  }
}
