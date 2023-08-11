package com.sparta.trelloproject.card.repository;

import com.sparta.trelloproject.card.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
    List<CardEntity> findAllByOrderByCreatedAtDesc();

    Optional<CardEntity> findByBoardIdAndColumnIdAndId(Long boardId, Long columnId, Long cardId);
}
