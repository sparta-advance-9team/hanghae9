package com.sparta.hanghaestartproject.v1_api.rest.like.repository;

import com.sparta.hanghaestartproject.v1_api.rest.like.model.LikePost;
import com.sparta.hanghaestartproject.v1_api.rest.post.model.Post;
import com.sparta.hanghaestartproject.v1_api.rest.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    LikePost findByPostAndUser(Post post, User user);
    Long countByPost(Post post);
     void deleteAllByPostId(Long id);
}
