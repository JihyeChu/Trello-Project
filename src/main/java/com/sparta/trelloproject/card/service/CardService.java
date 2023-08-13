package com.sparta.trelloproject.card.service;

import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.board.repository.BoardRepository;
import com.sparta.trelloproject.board.repository.BoardUserRepository;
import com.sparta.trelloproject.card.dto.*;
import com.sparta.trelloproject.card.entity.CardAssignEntity;
import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.card.repository.CardAssignRepository;
import com.sparta.trelloproject.card.repository.CardRepository;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import com.sparta.trelloproject.column.repository.ColumnRepository;
import com.sparta.trelloproject.column.service.ColumnService;
import com.sparta.trelloproject.user.entity.User;
import com.sparta.trelloproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CardService {

    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;
    private final CardRepository cardRepository;
    private final CardAssignRepository cardAssignRepository;
    private final ColumnService columnService;

    @Transactional
    public CardResponseDto createCard(CardRequestDto requestDto, User user, Long boardId, Long columnId) {
        BoardEntity board = findBoard(boardId);
        ColumnEntity column = findColumn(boardId, columnId);
        CardEntity card = new CardEntity(requestDto, user, board, column);
//        card.setUser(user);

        cardRepository.save(card);

        return new CardResponseDto(card);
    }


    @Transactional(readOnly = true)
    public CardListResponseDto getCards(Long boardId, Long columnId) {
        ColumnEntity column = findColumn(boardId, columnId);
        List<CardResponseDto> cardList = cardRepository.findAllByColumnOrderByCreatedAtDesc(column)
                .stream()
                .map(CardResponseDto::new)
                .toList();
        return new CardListResponseDto(cardList);
    }


    @Transactional(readOnly = true)
    public CardResponseDto getCardById(Long boardId, Long columnId, Long cardId) {
        CardEntity card = findCard(boardId, columnId, cardId);
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


    // 작업 할당
    @Transactional
    public CardAssignResponseDto assignTask(User user, Long boardId, Long columnId, Long cardId, CardAssignRequestDto requestDto) {
        BoardEntity board = findBoard(boardId);
        ColumnEntity column = findColumn(boardId, columnId);
        CardEntity card = findCard(boardId, columnId, cardId);

        if (columnService.checkOwnerCollaborater(user, board)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
//        boardUserRepository.findAllByOwnerUserAndCollaborateUserAndBoard(board.getUser(), user, board);

        CardAssignEntity assignEntity = new CardAssignEntity(requestDto, board, column, card);
        cardAssignRepository.save(assignEntity);

        return new CardAssignResponseDto(assignEntity);
    }

    public BoardEntity findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 보드는 존재하지 않습니다.")
        );
    }

    public ColumnEntity findColumn(Long boardId, Long columnId) {
        return columnRepository.findByBoardIdAndId(boardId, columnId).orElseThrow(
                () -> new IllegalArgumentException("선택한 칼럼은 존재하지 않습니다.")
        );
    }

    public CardEntity findCard(Long boardId, Long columnId, Long cardId) {
        return cardRepository.findByBoardIdAndColumnIdAndId(boardId, columnId, cardId).orElseThrow(
                () -> new IllegalArgumentException("선택한 카드는 존재하지 않습니다.")
        );
    }

    public CardEntity findCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("선택한 카드는 존재하지 않습니다.")
        );
    }
}
