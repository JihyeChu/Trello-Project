package com.sparta.trelloproject.card.dto;

import com.sparta.trelloproject.common.color.ColorEnum;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class CardRequestDto {

    private String cardName;
    private String description;
    private ColorEnum color;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime closingDate;
}
