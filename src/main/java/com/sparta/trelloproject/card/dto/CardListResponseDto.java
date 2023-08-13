package com.sparta.trelloproject.card.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CardListResponseDto {

    private List<CardResponseDto> cardList;

    public CardListResponseDto(List<CardResponseDto> cardList) {
        this.cardList = cardList;
    }

}
