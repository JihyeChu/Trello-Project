package com.sparta.trelloproject.board.entity;

import com.sparta.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardUser {

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
    Board board;

    public BoardUser(User collaborateUser, Board board) {
        this.ownerUser = board.getUser();
        this.collaborateUser = collaborateUser;
        this.board = board;
        board.addBaordUser(this);
    }
}
