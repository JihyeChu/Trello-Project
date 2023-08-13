package com.sparta.trelloproject.comment.repository;

import com.sparta.trelloproject.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findByBoardIdAndColumnIdAndCardIdAndId(Long boardId, Long columnId, Long cardId, Long commentId);
}
