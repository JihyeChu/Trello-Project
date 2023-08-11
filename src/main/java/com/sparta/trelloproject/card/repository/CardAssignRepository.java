package com.sparta.trelloproject.card.repository;

import com.sparta.trelloproject.card.entity.CardAssignEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardAssignRepository extends JpaRepository<CardAssignEntity, Long> {
}