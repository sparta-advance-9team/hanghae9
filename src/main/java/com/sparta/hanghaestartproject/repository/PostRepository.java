package com.sparta.hanghaestartproject.repository;

import com.sparta.hanghaestartproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
     
//     @EntityGraph(attributePaths = {"commentList"})
     List<Post> findAllByOrderByCreatedAtDesc();
     boolean existsByIdAndUsername(Long id, String username);
}
