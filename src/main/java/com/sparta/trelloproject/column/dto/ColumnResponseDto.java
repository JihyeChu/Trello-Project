package com.sparta.trelloproject.column.dto;

import com.sparta.trelloproject.card.dto.CardNameResponseDto;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ColumnResponseDto {
    private Long boardId;
    private Long columnId;
    private String userName;
    private String columnName;
    private List<CardNameResponseDto> cardList;

    public ColumnResponseDto(ColumnEntity column) {
        this.columnName = column.getColumnName();
        this.boardId = column.getBoard().getId();
        this.columnId = column.getId();
        this.userName = column.getUser().getUserName();
        this.cardList = column.getCardList().stream()
                .map(CardNameResponseDto::new)
                .toList();
    }
}
