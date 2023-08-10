package com.sparta.trelloproject.common.advice;

import com.sparta.trelloproject.common.api.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    // SignupRequestDto 의 Pattern Annotation 예외 처리
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponseDto> handleException(MethodArgumentNotValidException ex) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }

    // 프로젝트 전반적인 IllegalArgumentException 의 예외 처리(포스트맨에서 실습할 때 활성화)
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResponseDto> handleException(IllegalArgumentException ex) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }

    // 작성자 or Admin 이 아닌 경우
    @ExceptionHandler({RejectedExecutionException.class})
    public ResponseEntity<ApiResponseDto> handleException(RejectedExecutionException ex) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.FORBIDDEN);
    }

    // enum 클래스 내의 값이 아닌 다른 값을 입력할 경우
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ApiResponseDto> handleException(HttpMessageNotReadableException ex) {
        ApiResponseDto apiResponseDto = new ApiResponseDto("지정된 색만 입력할 수 있습니다.", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }

    //
}