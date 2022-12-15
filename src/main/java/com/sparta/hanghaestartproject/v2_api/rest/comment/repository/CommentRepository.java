package com.sparta.hanghaestartproject.v2_api.rest.comment.repository;

import com.sparta.hanghaestartproject.v1_api.rest.comment.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
     List<Comment> findAllByPostId(Long id, Pageable pageable);
     
}
