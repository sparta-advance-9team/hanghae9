package com.sparta.hanghaestartproject.v2_api.rest.like.repository;

import com.sparta.hanghaestartproject.v1_api.rest.comment.model.Comment;
import com.sparta.hanghaestartproject.v1_api.rest.like.model.LikeComment;
import com.sparta.hanghaestartproject.v1_api.rest.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

    LikeComment findByCommentAndUser(Comment comment, User user);

    Long countByComment(Comment comment);

}
