package com.sparta.trelloproject.user.dto;

import com.sparta.trelloproject.user.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {

    private String email;

    public ProfileResponseDto(User user) {
        this.email = user.getEmail();
    }
}
