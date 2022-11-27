package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.ArticleRequestDto;
import com.sparta.hanghaestartproject.dto.ArticleResponseDto;
import com.sparta.hanghaestartproject.entity.Article;
import com.sparta.hanghaestartproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
     
     private final ArticleRepository articleRepository;
     
     @Transactional (readOnly = true)
     public List<ArticleResponseDto> getArticles() {
          return articleRepository.findAllByOrderByCreatedAtDesc().stream()
               .map(ArticleResponseDto::new)
               .collect(Collectors.toList());
     }
     
     @Transactional
     public ArticleResponseDto createArticle(ArticleRequestDto requestDto) {
          Article article = new Article(requestDto);
          articleRepository.save(article);
          return new ArticleResponseDto(article);
     }
     @Transactional (readOnly = true)
     public ArticleResponseDto getArticle(Long id) {
          Article article = articleRepository.findById(id).orElseThrow(
               () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
          );
          return new ArticleResponseDto(article);
     }
     @Transactional
     public ArticleResponseDto updateArticle(Long id, ArticleRequestDto requestDto) {
          Article article = articleRepository.findByIdAndPassword(id, requestDto.getPassword()).orElseThrow(
               () -> new IllegalArgumentException("비밀번호가 틀립니다.")
          );
          article.update(requestDto);
          articleRepository.save(article);
          
          return new ArticleResponseDto(article);
     }
     @Transactional
     public boolean deleteArticle(Long id, String password) {
               System.out.println("deleteArticle>>>> id, password" + id +":"+ password);
          if(articleRepository.existsByIdAndPassword(id, password)){
               articleRepository.deleteById(id);
               return true;
          }
               System.out.println("deleteArticle>>>> not working");
          return false;
     }
}
