package com.sparta.trelloproject.board.entity;

import com.sparta.trelloproject.board.dto.BoardRequestDto;
import com.sparta.trelloproject.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "board_name", nullable = false)
  private String boardName;

  @Column
  private String description;

  @Column
  private String color;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "user_id")
//  User user;


  @Builder
  public Board(String boardName, String description, String color, User user) {
    this.boardName = boardName;
    this.description = description;
    this.color = color;
//    this.user = user;
  }

  public void update(String boardName, String description, String color) {
    this.boardName = boardName;
    this.description = description;
    this.color = color;
  }

}
