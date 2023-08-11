package com.sparta.trelloproject.comment.controller;

import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.card.service.CardService;
import com.sparta.trelloproject.comment.dto.CommentRequestDto;
import com.sparta.trelloproject.comment.dto.CommentResponseDto;
import com.sparta.trelloproject.comment.entity.CommentEntity;
import com.sparta.trelloproject.comment.service.CommentService;
import com.sparta.trelloproject.common.api.ApiResponseDto;
import com.sparta.trelloproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CardService cardService;

    // 댓글 생성
    @PostMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long boardId,
                                                            @PathVariable Long columnId,
                                                            @PathVariable Long cardId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(boardId, columnId, cardId, requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 댓글 수정
    @PutMapping("/cards/{cardId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long cardId,
                                                            @PathVariable Long commentId,
                                                            @RequestBody CommentRequestDto requestDto) {
        try {
            CardEntity card = cardService.findCard(cardId);
            CommentEntity comment = commentService.findComment(commentId);
            CommentResponseDto result = commentService.updateComment(card, comment, requestDto);
            return ResponseEntity.ok().body(result);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // 댓글 삭제
    @DeleteMapping("/cards/{cardId}/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long cardId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CardEntity card = cardService.findCard(cardId);
            CommentEntity comment = commentService.findComment(commentId);
            commentService.deleteComment(card, comment);
            return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }


}
