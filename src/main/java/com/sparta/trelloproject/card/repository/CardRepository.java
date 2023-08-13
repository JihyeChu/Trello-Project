package com.sparta.trelloproject.card.repository;

import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.column.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    List<CardEntity> findAllByColumnOrderByCreatedAtDesc(ColumnEntity column);
    Optional<CardEntity> findByBoardIdAndColumnIdAndId(Long boardId, Long columnId, Long cardId);
}
