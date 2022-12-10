package com.sparta.hanghaestartproject.repository;

import com.sparta.hanghaestartproject.entity.Comment;
import com.sparta.hanghaestartproject.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

}
