package com.sparta.trelloproject.user.service;

import com.sparta.trelloproject.user.dto.AuthRequestDto;
import com.sparta.trelloproject.user.entity.User;
import com.sparta.trelloproject.user.entity.UserRoleEnum;
import com.sparta.trelloproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder PasswordEncoder;

    public void signup(AuthRequestDto authRequestDto) {
        String userName = authRequestDto.getUserName();
        String password = PasswordEncoder.encode(authRequestDto.getPassword());
        String email = authRequestDto.getEmail();
        UserRoleEnum role = authRequestDto.getRole();

        if (userRepository.findByUserName(userName).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        User user = new User(userName, password, email, role);
        userRepository.save(user);
    }
}
