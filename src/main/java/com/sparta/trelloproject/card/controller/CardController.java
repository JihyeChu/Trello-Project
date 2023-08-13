package com.sparta.trelloproject.card.controller;

import com.sparta.trelloproject.card.dto.CardAssignRequestDto;
import com.sparta.trelloproject.card.dto.CardListResponseDto;
import com.sparta.trelloproject.card.dto.CardRequestDto;
import com.sparta.trelloproject.card.dto.CardResponseDto;
import com.sparta.trelloproject.card.service.CardService;
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
public class CardController {

    private final CardService cardService;

    @PostMapping("/boards/{boardId}/columns/{columnId}/cards")
    public ResponseEntity<ApiResponseDto> createCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long boardId,
                                                     @PathVariable Long columnId,
                                                     @RequestBody CardRequestDto requestDto) {
        cardService.createCard(requestDto, userDetails.getUser(), boardId, columnId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto("카드 생성 완료", HttpStatus.CREATED.value()));
    }


    @GetMapping("/boards/{boardId}/columns/{columnId}/cards")
    public ResponseEntity<CardListResponseDto> getCards(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long boardId,
                                                        @PathVariable Long columnId) {
        CardListResponseDto result = cardService.getCards(userDetails.getUser(), boardId, columnId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}")
    public ResponseEntity<CardResponseDto> getCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable Long boardId,
                                                   @PathVariable Long columnId,
                                                   @PathVariable Long cardId) {
        CardResponseDto result = cardService.getCardById(userDetails.getUser(), boardId, columnId, cardId);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}")
    public ResponseEntity<ApiResponseDto> updateCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long boardId,
                                                     @PathVariable Long columnId,
                                                     @PathVariable Long cardId,
                                                     @RequestBody CardRequestDto requestDto) {
        cardService.updateCard(userDetails.getUser(), boardId, columnId, cardId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("카드 수정 완료", HttpStatus.OK.value()));
    }

    @DeleteMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}")
    public ResponseEntity<ApiResponseDto> deleteCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long boardId,
                                                     @PathVariable Long columnId,
                                                     @PathVariable Long cardId) {
        cardService.deleteCard(userDetails.getUser(), boardId, columnId, cardId);
        return ResponseEntity.ok().body(new ApiResponseDto("카드 삭제 성공", HttpStatus.OK.value()));
    }

    // 작업 할당
    @PostMapping("boards/{boardId}/columns/{columnId}/cards/{cardId}/assign")
    public ResponseEntity<ApiResponseDto> assignTask(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long boardId,
                                                     @PathVariable Long columnId,
                                                     @PathVariable Long cardId,
                                                     @RequestBody CardAssignRequestDto requestDto) {
        cardService.assignTask(userDetails.getUser(), boardId, columnId, cardId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto("작업자 할당 완료", HttpStatus.CREATED.value()));
    }
}
