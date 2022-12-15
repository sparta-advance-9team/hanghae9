package com.sparta.hanghaestartproject.v2_api.rest.comment.repository;

import com.sparta.hanghaestartproject.v1_api.rest.comment.model.Comment;
import com.sparta.hanghaestartproject.v1_api.rest.comment.model.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {

    ReComment findByCommentAndUsername(Comment comment, String username);

}
