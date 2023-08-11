package com.sparta.trelloproject.column.dto;

import com.sparta.trelloproject.column.entity.ColumnEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnResponseDto {
    private String columnName;
    private Long boardId;
//    private List<CardResponseDto> cardList;

    public ColumnResponseDto(ColumnEntity column) {
        this.columnName = column.getColumnName();
        this.boardId = column.getBoard().getId();
//        this.cardList = column.getCardList().stream()
//                .map(ColumnResponseDto::new)
//                .sorted(Comparator.comparing(ColumnResponseDto::getCreateAt))
//                .toList();
    }
}
