package com.sparta.trelloproject.column.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ColumnRequestDto {
    @NotBlank
    private String columnName;
}
