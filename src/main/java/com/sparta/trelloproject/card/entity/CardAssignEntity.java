package com.sparta.trelloproject.card.entity;

import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.card.dto.CardAssignRequestDto;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import com.sparta.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "card_assign_tb")
public class CardAssignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private ColumnEntity column;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private CardEntity card;

    public CardAssignEntity(CardAssignRequestDto requestDto, BoardEntity board, ColumnEntity column, CardEntity card) {
        this.worker = requestDto.getWorker();
        this.board = board;
        this.column = column;
        this.card = card;
    }
}
