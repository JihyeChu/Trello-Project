package com.sparta.trelloproject.card.entity;

import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.card.dto.CardRequestDto;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import com.sparta.trelloproject.comment.entity.CommentEntity;
import com.sparta.trelloproject.common.color.ColorEnum;
import com.sparta.trelloproject.common.timestamped.TimeStamped;
import com.sparta.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "card_tb")
public class CardEntity extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cardname", nullable = false)
    private String cardName;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private ColorEnum color;

    @Column(name = "closingdate", nullable = false)
    private LocalDateTime closingDate;

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<CardAssignEntity> workerList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private ColumnEntity column;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<CommentEntity> commentList = new ArrayList<>();

    public CardEntity(CardRequestDto requestDto, User user, BoardEntity board, ColumnEntity column) {
        this.cardName = requestDto.getCardName();
        this.description = requestDto.getDescription();
        this.closingDate = requestDto.getClosingDate();
        this.color = requestDto.getColor();
        this.user = user;
        this.board = board;
        this.column = column;
    }

    public void update(CardRequestDto requestDto) {
        this.cardName = requestDto.getCardName();
        this.description = requestDto.getDescription();
        this.color = requestDto.getColor();
        this.closingDate = requestDto.getClosingDate();
    }
}
