package com.sparta.trelloproject.comment.service;

import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.board.service.BoardService;
import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.card.service.CardService;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import com.sparta.trelloproject.column.service.ColumnService;
import com.sparta.trelloproject.comment.dto.CommentRequestDto;
import com.sparta.trelloproject.comment.entity.CommentEntity;
import com.sparta.trelloproject.comment.repository.CommentRepository;
import com.sparta.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardService boardService;
    private final ColumnService columnService;
    private final CardService cardService;
    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(User user, Long boardId, Long columnId, Long cardId, CommentRequestDto requestDto) {
        BoardEntity board = boardService.findBoard(boardId);
        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        ColumnEntity column = columnService.findColumn(boardId, columnId);
        CardEntity card = cardService.findCard(boardId, columnId, cardId);
        CommentEntity comment = new CommentEntity(requestDto, user, board, column, card);
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(User user, Long boardId, Long columnId, Long cardId, Long commentId, CommentRequestDto requestDto) {
        BoardEntity board = boardService.findBoard(boardId);
        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        CommentEntity comment = findComment(boardId, columnId, cardId, commentId);

        comment.update(requestDto);
    }

    @Transactional
    public void deleteComment(User user, Long boardId, Long columnId, Long cardId, Long commentId) {
        BoardEntity board = boardService.findBoard(boardId);
        if (boardService.checkOwnerCollaborator(user, board)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        CommentEntity comment = findComment(boardId, columnId, cardId, commentId);

        commentRepository.delete(comment);
    }

    public CommentEntity findComment(Long boardId, Long columnId, Long cardId, Long commentId) {
        return commentRepository.findByBoardIdAndColumnIdAndCardIdAndId(boardId, columnId, cardId, commentId).orElseThrow(
                () -> new NullPointerException("작성한 댓글이 없습니다.")
        );
    }
}