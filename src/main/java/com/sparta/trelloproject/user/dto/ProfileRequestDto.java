package com.sparta.trelloproject.user.dto;

import com.sparta.trelloproject.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDto {

    private String password;
    private String email;

    public ProfileRequestDto(User user) {
        this.password = user.getPassword();
        this.email = user.getEmail();
    }
}
