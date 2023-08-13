package com.sparta.trelloproject.comment.controller;

import com.sparta.trelloproject.comment.dto.CommentRequestDto;
import com.sparta.trelloproject.comment.service.CommentService;
import com.sparta.trelloproject.common.api.ApiResponseDto;
import com.sparta.trelloproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}/comments")
    public ResponseEntity<ApiResponseDto> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long boardId,
                                                        @PathVariable Long columnId,
                                                        @PathVariable Long cardId,
                                                        @RequestBody CommentRequestDto requestDto) {
        commentService.createComment(userDetails.getUser(), boardId, columnId, cardId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto("댓글 생성 완료", HttpStatus.CREATED.value()));
    }

    // 댓글 수정
    @PutMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long boardId,
                                                        @PathVariable Long columnId,
                                                        @PathVariable Long cardId,
                                                        @PathVariable Long commentId,
                                                        @RequestBody CommentRequestDto requestDto) {
        commentService.updateComment(userDetails.getUser(), boardId, columnId, cardId, commentId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("댓글 수정 완료", HttpStatus.OK.value()));
    }


    // 댓글 삭제
    @DeleteMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long boardId,
                                                        @PathVariable Long columnId,
                                                        @PathVariable Long cardId,
                                                        @PathVariable Long commentId) {
        commentService.deleteComment(userDetails.getUser(), boardId, columnId, cardId, commentId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
    }
}