package com.sparta.trelloproject.board.entity;

import com.sparta.trelloproject.column.entity.ColumnEntity;
import com.sparta.trelloproject.common.color.ColorEnum;
import com.sparta.trelloproject.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "board_name", nullable = false)
  private String boardName;

  @Column
  private String description;

  @Enumerated(EnumType.STRING)
  private ColorEnum color;

  // 보드의 생성자(주인) 유저
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
  private List<BoardUserEntity> boardUsers = new ArrayList<>();

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
  private List<ColumnEntity> columns = new ArrayList<>();

  @Builder
  public BoardEntity(String boardName, String description, ColorEnum color, User user) {
    this.boardName = boardName;
    this.description = description;
    this.color = color;
    this.user = user;
  }

  public void update(String boardName, String description, ColorEnum color) {
    this.boardName = boardName;
    this.description = description;
    this.color = color;
  }

  public void addBaordUser(BoardUserEntity boardUser) {
    this.boardUsers.add(boardUser);
  }

}
