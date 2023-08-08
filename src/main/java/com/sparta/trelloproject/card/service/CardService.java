package com.sparta.trelloproject.card.service;

import com.sparta.trelloproject.card.dto.CardListResponseDto;
import com.sparta.trelloproject.card.dto.CardRequestDto;
import com.sparta.trelloproject.card.dto.CardResponseDto;
import com.sparta.trelloproject.card.entity.Card;
import com.sparta.trelloproject.card.repository.CardRepository;
import com.sparta.trelloproject.common.security.UserDetailsImpl;
import com.sparta.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    @Transactional
    public CardResponseDto createCard(CardRequestDto requestDto, User user) {
        Card card = new Card(requestDto);
        card.setUser(user);

        cardRepository.save(card);

        return new CardResponseDto(card);
    }

    @Transactional(readOnly = true)
    public CardListResponseDto getCards() {
        List<CardResponseDto> cardList = cardRepository.findAllByOrderByCreatedAtDesc().stream().map(CardResponseDto::new).toList();
        return new CardListResponseDto(cardList);
    }

    @Transactional(readOnly = true)
    public CardResponseDto getCardById(Long id) {
        Card card = findCard(id);
        return new CardResponseDto(card);
    }

    @Transactional
    public CardResponseDto updateCard(Card card, CardRequestDto requestDto) {
       card.update(requestDto);

       return new CardResponseDto(card);
    }

    public void deleteCard(Card card) {
        cardRepository.delete(card);
    }

    public Card findCard(Long id){
        return cardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 카드는 존재하지 않습니다.")
        );
    }

}
