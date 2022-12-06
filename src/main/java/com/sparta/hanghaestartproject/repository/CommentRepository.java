package com.sparta.hanghaestartproject.repository;

import com.sparta.hanghaestartproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
