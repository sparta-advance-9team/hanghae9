package com.sparta.hanghaestartproject.repository;

import com.sparta.hanghaestartproject.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
     
//     @EntityGraph(attributePaths = {"commentList"})
     List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
     boolean existsByIdAndUsername(Long id, String username);
     
     void deleteAllByUsername(String username);
}
