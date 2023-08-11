package com.sparta.trelloproject.card.repository;

import com.sparta.trelloproject.card.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
    List<CardEntity> findAllByOrderByCreatedAtDesc();
}
