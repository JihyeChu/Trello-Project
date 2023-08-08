package com.sparta.trelloproject.card.controller;

import com.sparta.trelloproject.card.dto.CardListResponseDto;
import com.sparta.trelloproject.card.dto.CardRequestDto;
import com.sparta.trelloproject.card.dto.CardResponseDto;
import com.sparta.trelloproject.card.entity.Card;
import com.sparta.trelloproject.card.service.CardService;
import com.sparta.trelloproject.common.api.ApiResponseDto;
import com.sparta.trelloproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/cards")
    public ResponseEntity<CardResponseDto> createCard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CardRequestDto requestDto) {
        CardResponseDto result = cardService.createCard(requestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(result);
    }

    @GetMapping("/cards")
    public ResponseEntity<CardListResponseDto> getCards() {
        CardListResponseDto result = cardService.getCards();

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<CardResponseDto> getCard(@PathVariable Long id) {
        CardResponseDto result = cardService.getCardById(id);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/cards/{id}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long id, @RequestBody CardRequestDto requestDto) {
        try {
            Card card = cardService.findCard(id);
            CardResponseDto result = cardService.updateCard(card, requestDto);
            return ResponseEntity.ok().body(result);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<ApiResponseDto> deleteCard(@PathVariable Long id){
        try{
            Card card = cardService.findCard(id);
            cardService.deleteCard(card);
            return ResponseEntity.ok().body(new ApiResponseDto("삭제 성공", HttpStatus.OK.value()));
        }catch(RejectedExecutionException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
