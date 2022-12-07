package com.sparta.hanghaestartproject.repository;

import com.sparta.hanghaestartproject.entity.Article;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
     
//     @EntityGraph(attributePaths = {"commentList"})
     List<Article> findAllByOrderByCreatedAtDesc();
     boolean existsByIdAndUsername(Long id, String username);
}
