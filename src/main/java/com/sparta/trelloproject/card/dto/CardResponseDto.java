package com.sparta.trelloproject.card.dto;

import com.sparta.trelloproject.card.entity.CardAssignEntity;
import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.comment.dto.CommentResponseDto;
import com.sparta.trelloproject.common.color.ColorEnum;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CardResponseDto {
    private Long cardId;
    private String cardName;
    private String description;
    private ColorEnum color;
    private LocalDateTime closingDate;
    private LocalDateTime createAt;
    private LocalDateTime modified;
    private List<CardAssignResponseDto> worker;
    private List<CommentResponseDto> comments;

    public CardResponseDto(CardEntity card) {
        this.cardId = card.getId();
        this.cardName = card.getCardName();
        this.description = card.getDescription();
        this.color = card.getColor();
        this.closingDate = card.getClosingDate();
        this.createAt = card.getCreatedAt();
        this.modified = card.getModifiedAt();
        this.worker = new ArrayList<>();
        for (CardAssignEntity assignEntity : card.getWorkerList()) {
            worker.add(new CardAssignResponseDto(assignEntity));
        }
        this.comments = card.getCommentList().stream()
                .map(CommentResponseDto::new)
                .toList();
    }
}
