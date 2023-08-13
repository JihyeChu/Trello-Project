package com.sparta.trelloproject.comment.entity;

import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import com.sparta.trelloproject.comment.dto.CommentRequestDto;
import com.sparta.trelloproject.common.timestamped.Timestamped;
import com.sparta.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment_tb")
public class CommentEntity extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private ColumnEntity column;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private CardEntity card;

    public CommentEntity(CommentRequestDto requestDto, User user, BoardEntity board, ColumnEntity column, CardEntity card) {
        this.comment = requestDto.getComment();
        this.username = user.getUserName();
        this.user = user;
        this.board = board;
        this.column = column;
        this.card = card;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}