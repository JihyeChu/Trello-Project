package com.sparta.trelloproject.card.repository;

import com.sparta.trelloproject.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByOrderByCreatedAtDesc();
}
