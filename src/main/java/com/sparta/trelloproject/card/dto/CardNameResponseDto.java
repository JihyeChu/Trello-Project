package com.sparta.trelloproject.card.dto;

import com.sparta.trelloproject.card.entity.CardEntity;
import lombok.Getter;

@Getter
public class CardNameResponseDto {
  private Long cardId;
  private String madeCardUserName;
  private String cardName;

  public CardNameResponseDto(CardEntity card) {
    this.cardId = card.getId();
    this.madeCardUserName = card.getUser().getUserName();
    this.cardName = card.getCardName();
  }
}
