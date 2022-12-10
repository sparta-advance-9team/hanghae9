package com.sparta.hanghaestartproject.repository;

import com.sparta.hanghaestartproject.entity.LikeComment;
import com.sparta.hanghaestartproject.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {

}
