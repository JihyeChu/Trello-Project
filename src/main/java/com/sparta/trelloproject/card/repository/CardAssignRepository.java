package com.sparta.trelloproject.card.repository;

import com.sparta.trelloproject.card.entity.CardAssignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardAssignRepository extends JpaRepository<CardAssignEntity, Long> {
}