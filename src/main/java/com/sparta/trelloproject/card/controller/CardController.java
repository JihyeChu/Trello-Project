package com.sparta.trelloproject.card.controller;

import com.sparta.trelloproject.card.dto.*;
import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.card.service.CardService;
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
public class CardController {

    private final CardService cardService;

    @PostMapping("/boards/{boardId}/columns/{columnId}/cards")
    public ResponseEntity<CardResponseDto> createCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable("boardId") Long boardId,
                                                      @PathVariable("columnId") Long columnId,
                                                      @RequestBody CardRequestDto requestDto) {
        CardResponseDto result = cardService.createCard(requestDto, userDetails.getUser(), boardId, columnId);

        return ResponseEntity.status(201).body(result);
    }


    @GetMapping("/columns/{columnId}/cards")
    public ResponseEntity<CardListResponseDto> getCards() {
        CardListResponseDto result = cardService.getCards();

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}")
    public ResponseEntity<CardResponseDto> getCard(@PathVariable Long boardId,
                                                   @PathVariable Long columnId,
                                                   @PathVariable Long cardId) {
        CardResponseDto result = cardService.getCardById(boardId, columnId, cardId);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long boardId,
                                                      @PathVariable Long columnId,
                                                      @PathVariable Long cardId, @RequestBody CardRequestDto requestDto) {
        try {
            CardEntity card = cardService.findCard(boardId, columnId, cardId);
            CardResponseDto result = cardService.updateCard(card, requestDto);
            return ResponseEntity.ok().body(result);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}")
    public ResponseEntity<ApiResponseDto> deleteCard(@PathVariable Long boardId,
                                                     @PathVariable Long columnId,
                                                     @PathVariable Long cardId) {
        try {
            CardEntity card = cardService.findCard(boardId, columnId, cardId);
            cardService.deleteCard(card);
            return ResponseEntity.ok().body(new ApiResponseDto("삭제 성공", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 작업 할당
    @PostMapping("boards/{boardId}/columns/{columnId}/cards/{cardId}/assignTask")
    public ResponseEntity<CardAssignResponseDto> assignTask(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @PathVariable Long boardId,
                                                            @PathVariable Long columnId,
                                                            @PathVariable Long cardId,
                                                            @RequestBody CardAssignRequestDto requestDto) {
        CardAssignResponseDto result = cardService.assignTask(userDetails.getUser(), boardId, columnId, cardId, requestDto);
        return ResponseEntity.status(201).body(result);
    }

}
