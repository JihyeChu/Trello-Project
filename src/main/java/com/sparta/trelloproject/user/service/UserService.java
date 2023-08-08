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

    public void login(AuthRequestDto authRequestDto) {
        String userName = authRequestDto.getUserName();
        String password = authRequestDto.getPassword();

        //Id 확인
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("Id가 틀렸습니다.")
        );

        //패스워드 확인
        if (!PasswordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
