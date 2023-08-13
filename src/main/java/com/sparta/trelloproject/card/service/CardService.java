package com.sparta.trelloproject.card.service;

import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.board.service.BoardService;
import com.sparta.trelloproject.card.dto.CardAssignRequestDto;
import com.sparta.trelloproject.card.dto.CardListResponseDto;
import com.sparta.trelloproject.card.dto.CardRequestDto;
import com.sparta.trelloproject.card.dto.CardResponseDto;
import com.sparta.trelloproject.card.entity.CardAssignEntity;
import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.card.repository.CardAssignRepository;
import com.sparta.trelloproject.card.repository.CardRepository;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import com.sparta.trelloproject.column.service.ColumnService;
import com.sparta.trelloproject.user.entity.User;
import com.sparta.trelloproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CardService {

    private final BoardService boardService;
    private final ColumnService columnService;
    private final UserService userService;
    private final CardRepository cardRepository;
    private final CardAssignRepository cardAssignRepository;

    @Transactional
    public void createCard(CardRequestDto requestDto, User user, Long boardId, Long columnId) {
        BoardEntity board = boardService.findBoard(boardId);
        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        ColumnEntity column = columnService.findColumn(boardId, columnId);
        CardEntity card = new CardEntity(requestDto, user, board, column);
//        card.setUser(user);

        cardRepository.save(card);
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
    public void assignTask(User user, Long boardId, Long columnId, Long cardId, CardAssignRequestDto requestDto) {
        BoardEntity board = boardService.findBoard(boardId);
        ColumnEntity column = columnService.findColumn(boardId, columnId);
        CardEntity card = findCard(boardId, columnId, cardId);

        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
//        boardUserRepository.findAllByOwnerUserAndCollaborateUserAndBoard(board.getUser(), user, board);

        User findUser = userService.findUser(requestDto.getWorker());
        if (boardService.checkOwnerCollaborator(findUser, board)) {
            throw new IllegalArgumentException("보드에 초대된 유저가 아닙니다.");
        }

        CardAssignEntity assignEntity = new CardAssignEntity(requestDto, board, column, card);
        cardAssignRepository.save(assignEntity);
    }

    public CardEntity findCard(Long boardId, Long columnId, Long cardId) {
        return cardRepository.findByBoardIdAndColumnIdAndId(boardId, columnId, cardId).orElseThrow(
                () -> new IllegalArgumentException("선택한 카드는 존재하지 않습니다.")
        );
    }
}
