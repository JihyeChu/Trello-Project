package com.sparta.trelloproject.card.entity;

import com.sparta.trelloproject.card.dto.CardRequestDto;
import com.sparta.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="card_table")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="cardname", nullable = false)
    private String cardName;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="color")
    private String color;

    @Column(name="closingdate", nullable = false)
    private String closingDate;

    @Column(name="worker", nullable = false)
    private String worker;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="board_id")
//    private Board board;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "column_id")
//    private Column column;
//
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public Card(CardRequestDto requestDto){
        this.cardName = requestDto.getCardName();
        this.description = requestDto.getDescription();
        this.color = requestDto.getColor();
        this.closingDate = requestDto.getClosingDate();
        this.worker = requestDto.getWorker();
    }

    public void update(CardRequestDto requestDto) {
        this.cardName = requestDto.getCardName();
        this.description = requestDto.getDescription();
        this.color = requestDto.getColor();
        this.closingDate = requestDto.getClosingDate();
    }
}
