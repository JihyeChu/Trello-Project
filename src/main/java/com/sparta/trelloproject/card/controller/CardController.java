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
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/columns/{columnId}/cards")
    public ResponseEntity<CardResponseDto> createCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable("columnId") Long columnId,
                                                      @RequestBody CardRequestDto requestDto) {
        CardResponseDto result = cardService.createCard(requestDto, userDetails.getUser(), columnId);

        return ResponseEntity.status(201).body(result);
    }


    @GetMapping("/columns/{columnId}/cards")
    public ResponseEntity<CardListResponseDto> getCards() {
        CardListResponseDto result = cardService.getCards();

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDto> getCard(@PathVariable Long id) {
        CardResponseDto result = cardService.getCardById(id);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long id, @RequestBody CardRequestDto requestDto) {
        try {
            CardEntity card = cardService.findCard(id);
            CardResponseDto result = cardService.updateCard(card, requestDto);
            return ResponseEntity.ok().body(result);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteCard(@PathVariable Long id){
        try{
            CardEntity card = cardService.findCard(id);
            cardService.deleteCard(card);
            return ResponseEntity.ok().body(new ApiResponseDto("삭제 성공", HttpStatus.OK.value()));
        }catch(RejectedExecutionException e){
            return ResponseEntity.badRequest().build();
        }
    }

    // 작업 할당
    @PostMapping("/columns/{columnId}/cards/{cardId}")
    public ResponseEntity<CardAssignResponseDto> assignTask(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @PathVariable Long columnId,
                                                            @PathVariable Long cardId,
                                                            @RequestBody CardAssignRequestDto requestDto){
        CardAssignResponseDto result = cardService.assignTask(userDetails.getUser(), columnId, cardId, requestDto);
        return ResponseEntity.status(201).body(result);
    }

}
