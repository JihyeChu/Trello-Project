package com.sparta.trelloproject.comment.repository;

import com.sparta.trelloproject.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
