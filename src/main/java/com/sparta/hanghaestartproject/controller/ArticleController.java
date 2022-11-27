package com.sparta.hanghaestartproject.controller;

import com.sparta.hanghaestartproject.dto.ArticleDeleteRequestDto;
import com.sparta.hanghaestartproject.dto.ArticleDeleteResponseDto;
import com.sparta.hanghaestartproject.dto.ArticleRequestDto;
import com.sparta.hanghaestartproject.dto.ArticleResponseDto;
import com.sparta.hanghaestartproject.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ArticleController {
     
     private final ArticleService articleService;
     
     @GetMapping("/api/articles") // 전체 게시글 목록 조회 API
     public List<ArticleResponseDto> getArticles(){
          return articleService.getArticles();
     }
     
     @PostMapping("/api/article") // 게시글 작성 API
     public ArticleResponseDto createArticle(@RequestBody ArticleRequestDto requestDto){
          return articleService.createArticle(requestDto);
     }
     
     @GetMapping("/api/article/{id}") // 선택한 게시글 조회 API
     public ArticleResponseDto getArticle(@PathVariable Long id){
          return articleService.getArticle(id);
     }
     
     @PutMapping("/api/article/{id}") // 선택한 게시글 수정
     public ArticleResponseDto updateArticle(@PathVariable Long id, @RequestBody ArticleRequestDto requestDto){
          return articleService.updateArticle(id, requestDto);
     }
     
     @DeleteMapping("/api/article/{id}") // 선택한 게시글 수정
     public ArticleDeleteResponseDto deleteArticle(@PathVariable Long id, @RequestBody ArticleDeleteRequestDto requestDto){
          boolean deleteResult = articleService.deleteArticle(id, requestDto.getPassword());
          return new ArticleDeleteResponseDto(deleteResult);
     }
}
