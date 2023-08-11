package com.sparta.trelloproject.column.dto;

import com.sparta.trelloproject.column.entity.ColumnEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnResponseDto {
    private Long boardId;
    private String userName;
    private String columnName;
//    private List<CardResponseDto> cardList;

    public ColumnResponseDto(ColumnEntity column) {
        this.columnName = column.getColumnName();
        this.boardId = column.getBoard().getId();
        this.userName = column.getUser().getUserName();
//        this.cardList = column.getCardList().stream()
//                .map(ColumnResponseDto::new)
//                .sorted(Comparator.comparing(ColumnResponseDto::getCreateAt))
//                .toList();
    }
}
