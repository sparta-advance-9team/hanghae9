package com.sparta.hanghaestartproject.repository;

import com.sparta.hanghaestartproject.entity.Comment;
import com.sparta.hanghaestartproject.entity.ReComment;
import com.sparta.hanghaestartproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {

    ReComment findByCommentAndUsername(Comment comment, String username);

}
