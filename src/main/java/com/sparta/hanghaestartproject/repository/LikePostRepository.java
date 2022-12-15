package com.sparta.hanghaestartproject.repository;

import com.sparta.hanghaestartproject.entity.LikeComment;
import com.sparta.hanghaestartproject.entity.LikePost;
import com.sparta.hanghaestartproject.entity.Post;
import com.sparta.hanghaestartproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    LikePost findByPostAndUser(Post post, User user);
    Long countByPost(Post post);
     void deleteAllByPostId(Long id);
}
