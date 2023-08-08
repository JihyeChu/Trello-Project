package com.sparta.trelloproject.card.dto;

import com.sparta.trelloproject.card.entity.Card;
import lombok.Getter;

@Getter
public class CardResponseDto {

    private String cardName;
    private String description;
    private String color;
    private String closingDate;
    private String worker;

    public CardResponseDto(Card card) {
        this.cardName = card.getCardName();
        this.description = card.getDescription();
        this.color = card.getColor();
        this.closingDate = card.getClosingDate();
        this.worker = card.getWorker();
    }
}
