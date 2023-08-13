package com.sparta.trelloproject.user.dto;

import lombok.Getter;

@Getter
public class PasswordRequestDto {
    private String password;
    private String newPassword;
    private String checkNewPassword;
}
