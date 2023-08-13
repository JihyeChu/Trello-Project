package com.sparta.trelloproject.card.service;

import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.board.service.BoardService;
import com.sparta.trelloproject.card.dto.*;
import com.sparta.trelloproject.card.entity.CardAssignEntity;
import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.card.repository.CardAssignRepository;
import com.sparta.trelloproject.card.repository.CardRepository;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import com.sparta.trelloproject.column.service.ColumnService;
import com.sparta.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CardService {

    private final BoardService boardService;
    private final ColumnService columnService;
    private final CardRepository cardRepository;
    private final CardAssignRepository cardAssignRepository;

    @Transactional
    public CardResponseDto createCard(CardRequestDto requestDto, User user, Long boardId, Long columnId) {
        BoardEntity board = boardService.findBoard(boardId);
        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        ColumnEntity column = columnService.findColumn(boardId, columnId);
        CardEntity card = new CardEntity(requestDto, user, board, column);
//        card.setUser(user);

        cardRepository.save(card);

        return new CardResponseDto(card);
    }


    @Transactional(readOnly = true)
    public CardListResponseDto getCards(User user, Long boardId, Long columnId) {
        BoardEntity board = boardService.findBoard(boardId);
        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        ColumnEntity column = columnService.findColumn(boardId, columnId);
        List<CardResponseDto> cardList = cardRepository.findAllByColumnOrderByCreatedAtDesc(column)
                .stream()
                .map(CardResponseDto::new)
                .toList();
        return new CardListResponseDto(cardList);
    }

    @Transactional(readOnly = true)
    public CardResponseDto getCardById(User user, Long boardId, Long columnId, Long cardId) {
        BoardEntity board = boardService.findBoard(boardId);
        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        CardEntity card = findCard(boardId, columnId, cardId);
        return new CardResponseDto(card);
    }

    @Transactional
    public void updateCard(User user, Long boardId, Long columnId, Long cardId, CardRequestDto requestDto) {
        BoardEntity board = boardService.findBoard(boardId);
        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        CardEntity card = findCard(boardId, columnId, cardId);
        card.update(requestDto);
    }

    @Transactional
    public void deleteCard(User user, Long boardId, Long columnId, Long cardId) {
        BoardEntity board = boardService.findBoard(boardId);
        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        CardEntity card = findCard(boardId, columnId, cardId);
        cardRepository.delete(card);
    }


    // 작업 할당
    @Transactional
    public CardAssignResponseDto assignTask(User user, Long boardId, Long columnId, Long cardId, CardAssignRequestDto requestDto) {
        BoardEntity board = boardService.findBoard(boardId);
        ColumnEntity column = columnService.findColumn(boardId, columnId);
        CardEntity card = findCard(boardId, columnId, cardId);

        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
//        boardUserRepository.findAllByOwnerUserAndCollaborateUserAndBoard(board.getUser(), user, board);

        CardAssignEntity assignEntity = new CardAssignEntity(requestDto, board, column, card);
        cardAssignRepository.save(assignEntity);

        return new CardAssignResponseDto(assignEntity);
    }

    public CardEntity findCard(Long boardId, Long columnId, Long cardId) {
        return cardRepository.findByBoardIdAndColumnIdAndId(boardId, columnId, cardId).orElseThrow(
                () -> new IllegalArgumentException("선택한 카드는 존재하지 않습니다.")
        );
    }
}
