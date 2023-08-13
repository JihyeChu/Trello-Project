package com.sparta.trelloproject.user.controller;

import com.sparta.trelloproject.common.api.ApiResponseDto;
import com.sparta.trelloproject.common.security.UserDetailsImpl;
import com.sparta.trelloproject.user.dto.AuthRequestDto;
import com.sparta.trelloproject.user.dto.PasswordRequestDto;
import com.sparta.trelloproject.user.dto.ProfileRequestDto;
import com.sparta.trelloproject.user.dto.ProfileResponseDto;
import com.sparta.trelloproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> sighUp(@RequestBody AuthRequestDto authRequestDto) {

        try {
            userService.signup(authRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("중복된 Id 입니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    //프로필 조회
    @GetMapping("/profile")
    public ProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getProfile(userDetails.getUser().getId());
    }

    //프로필 수정
    @PutMapping("/profile")
    public ResponseEntity<ApiResponseDto> updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto profileRequestDto) {
        try {
            userService.updateProfile(userDetails.getUser().getId(), profileRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("정보를 수정할 수 없습니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new ApiResponseDto("회원정보 수정 성공", HttpStatus.OK.value()));
    }

    //비밀번호 수정
    @PutMapping("/password")
    public ResponseEntity<ApiResponseDto> updatePassword(@RequestBody PasswordRequestDto passwordRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            userService.updatePassword(passwordRequestDto, userDetails.getUser().getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("비밀번호 수정 실패!!" + e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new ApiResponseDto("비밀번호 수정 성공!!", HttpStatus.OK.value()));
    }
}
