package com.sparta.hanghaestartproject.repository;

import com.sparta.hanghaestartproject.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
     List<Article> findAllByOrderByCreatedAtDesc();
     Optional<Article> findByIdAndPassword(Long id, String password);
     Boolean existsByIdAndPassword(Long id, String password);
}
