package com.sparta.hanghaestartproject.repository;

import com.sparta.hanghaestartproject.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

    LikeComment findByCommentAndUser(Comment comment, User user);

    Long countByComment(Comment comment);

}
