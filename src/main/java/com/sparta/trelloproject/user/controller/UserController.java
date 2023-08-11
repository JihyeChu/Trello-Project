package com.sparta.trelloproject.user.controller;

import com.sparta.trelloproject.common.api.ApiResponseDto;
import com.sparta.trelloproject.common.jwt.JwtUtil;
import com.sparta.trelloproject.user.dto.AuthRequestDto;
import com.sparta.trelloproject.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> sighUp(@RequestBody AuthRequestDto authRequestDto) {

        try {
            userService.signup(authRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("중복된 Id 입니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }
}
