package com.sparta.trelloproject.comment.service;

import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.card.service.CardService;
import com.sparta.trelloproject.comment.dto.CommentRequestDto;
import com.sparta.trelloproject.comment.dto.CommentResponseDto;
import com.sparta.trelloproject.comment.entity.CommentEntity;
import com.sparta.trelloproject.comment.repository.CommentRepository;
import com.sparta.trelloproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CardService cardService;
    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(Long boardId, Long columnId, Long cardId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        CardEntity card = cardService.findCard(boardId, columnId, cardId);
        CommentEntity comment = new CommentEntity(requestDto, userDetails, card);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(CardEntity card, CommentEntity comment, CommentRequestDto requestDto) {
        comment.update(requestDto, card);
        return new CommentResponseDto(comment);
    }

    public void deleteComment(CardEntity card, CommentEntity comment) {
        commentRepository.delete(comment);
    }

    public CommentEntity findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("작성한 댓글이 없습니다.")
        );
    }

}