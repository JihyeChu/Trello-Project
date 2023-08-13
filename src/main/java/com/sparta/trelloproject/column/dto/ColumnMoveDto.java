package com.sparta.trelloproject.column.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ColumnMoveDto {
    @NotBlank
    private int selectColumn;

    @NotBlank
    private int selectIndex;
}
