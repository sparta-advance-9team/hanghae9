package com.sparta.hanghaestartproject.v2_api.rest.post.repository;

import com.sparta.hanghaestartproject.v1_api.rest.post.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
     
//     @EntityGraph(attributePaths = {"commentList"})
     List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
     boolean existsByIdAndUsername(Long id, String username);

}
