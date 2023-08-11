package com.sparta.trelloproject.card.entity;

import com.sparta.trelloproject.card.dto.CardAssignRequestDto;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import com.sparta.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name="cardassign_tb")
public class CardAssignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="card_id")
    private CardEntity card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public CardAssignEntity(CardAssignRequestDto requestDto) {
        this.worker = requestDto.getWorker();
    }
}
