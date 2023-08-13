package com.sparta.trelloproject.column.dto;

import com.sparta.trelloproject.card.dto.CardNameResponseDto;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ColumnNameResponseDto {
    private Long columnId;
    private String madeColumnUserName;
    private String columnName;
    private List<CardNameResponseDto> cards;

    public ColumnNameResponseDto(ColumnEntity column) {
        this.madeColumnUserName = column.getUser().getUserName();
        this.columnId = column.getId();
        this.columnName = column.getColumnName();
        this.cards = column.getCardList().stream()
                .map(CardNameResponseDto::new)
                .collect(Collectors.toList());
    }
}
