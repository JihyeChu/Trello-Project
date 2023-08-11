package com.sparta.trelloproject.card.service;

import com.sparta.trelloproject.board.entity.BoardUserEntity;
import com.sparta.trelloproject.board.repository.BoardUserRepository;
import com.sparta.trelloproject.card.dto.*;
import com.sparta.trelloproject.card.entity.CardAssignEntity;
import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.card.repository.CardAssignRepository;
import com.sparta.trelloproject.card.repository.CardRepository;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import com.sparta.trelloproject.column.repository.ColumnRepository;
import com.sparta.trelloproject.common.security.UserDetailsImpl;
import com.sparta.trelloproject.user.entity.User;
import com.sparta.trelloproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CardService {

    private final ColumnRepository columnRepository;
    private final CardRepository cardRepository;
    private final CardAssignRepository cardAssignRepository;
    private final BoardUserRepository boardUserRepository;
    private final UserRepository userRepository;

    @Transactional
    public CardResponseDto createCard(CardRequestDto requestDto, User user, Long columnId) {
        ColumnEntity column = findColumn(columnId);
        CardEntity card = new CardEntity(requestDto, user, column);
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
        CardEntity card = findCard(id);
        return new CardResponseDto(card);
    }

    @Transactional
    public CardResponseDto updateCard(CardEntity card, CardRequestDto requestDto) {
       card.update(requestDto);

       return new CardResponseDto(card);
    }

    public void deleteCard(CardEntity card) {
        cardRepository.delete(card);
    }

    public ColumnEntity findColumn(Long id){
        return columnRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 칼럼은 존재하지 않습니다.")
        );
    }

    // 작업 할당
    @Transactional
    public CardAssignResponseDto assignTask(User user, Long columnId, Long cardId, CardAssignRequestDto requestDto) {
        ColumnEntity column = findColumn(columnId);

        boardUserRepository.findByUserAndBoardId(user.getId(), column.getBoard().getId()).orElseThrow(() ->
                new IllegalArgumentException("보드에 속한 유저가 아닙니다."));

        CardAssignEntity assignEntity = new CardAssignEntity(requestDto);
        cardAssignRepository.save(assignEntity);

        return new CardAssignResponseDto(assignEntity);
    }

    // 작업 할당 whghl


    public CardEntity findCard(Long id){
        return cardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 카드는 존재하지 않습니다.")
        );
    }
}
