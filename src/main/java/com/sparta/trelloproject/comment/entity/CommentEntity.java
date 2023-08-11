package com.sparta.trelloproject.comment.entity;

import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.comment.dto.CommentRequestDto;
import com.sparta.trelloproject.common.security.UserDetailsImpl;
import com.sparta.trelloproject.common.timestamped.TimeStamped;
import com.sparta.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor
@Table(name="comment")
public class CommentEntity extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="card_id", nullable = false)
    private CardEntity card;

    public CommentEntity(CommentRequestDto requestDto, UserDetailsImpl userDetails, CardEntity card) {
        this.comment = requestDto.getComment();
        this.username = userDetails.getUsername();
        this.user = userDetails.getUser();
        this.card = card;
    }

    public void update(CommentRequestDto requestDto,CardEntity card) {
        this.comment = requestDto.getComment();
        this.card = card;

    }
}