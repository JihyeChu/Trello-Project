package com.sparta.trelloproject.card.dto;

import lombok.Getter;

@Getter
public class CardRequestDto {

    private Long id;
    private String cardName;
    private String description;
    private String color;
    private String closingDate;
    private String worker;

}
