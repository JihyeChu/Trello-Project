package com.sparta.trelloproject.user.dto;

import com.sparta.trelloproject.user.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {

    private String password;
    private String email;

    public ProfileResponseDto(User user) {
        this.password = user.getPassword();
        this.email = user.getEmail();
    }
}
