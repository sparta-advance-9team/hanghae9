package com.sparta.hanghaestartproject.repository;

import com.sparta.hanghaestartproject.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
     List<Comment> findAllByPostId(Long id, Pageable pageable);
     
     void deleteAllByUsername(String username);
}
