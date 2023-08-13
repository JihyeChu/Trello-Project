package com.sparta.trelloproject.board.entity;

import com.sparta.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "board_user_tb")
public class BoardUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_user_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User ownerUser;

    @ManyToOne
    @JoinColumn(name = "collaborate_user_id", nullable = false)
    private User collaborateUser;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private BoardEntity board;

    public BoardUserEntity(User collaborateUser, BoardEntity board) {
        this.ownerUser = board.getUser();
        this.collaborateUser = collaborateUser;
        this.board = board;
        board.addBaordUser(this);
    }
}
