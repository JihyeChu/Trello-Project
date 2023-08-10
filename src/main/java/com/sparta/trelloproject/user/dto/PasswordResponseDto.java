package com.sparta.trelloproject.user.dto;

import com.sparta.trelloproject.user.entity.User;
import lombok.Getter;

@Getter
public class PasswordResponseDto {
    private String password;

    public PasswordResponseDto(User user) {
        this.password = user.getPassword();
    }
}