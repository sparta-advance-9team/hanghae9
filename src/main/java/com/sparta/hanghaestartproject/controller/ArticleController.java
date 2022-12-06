package com.sparta.hanghaestartproject.controller;

import com.sparta.hanghaestartproject.dto.*;
import com.sparta.hanghaestartproject.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {
     
     private final ArticleService articleService;
     
     // 전체 게시글 목록 조회 API
     //- 제목, 작성자명(username), 작성 내용, 작성 날짜를 조회하기
     //- 작성 날짜 기준 내림차순으로 정렬하기
     @GetMapping("/api/articles")
     public List<ArticleResponseDto> getArticles(){
          return articleService.getArticles();
     }
     
     // 게시글 작성 API
     //- 토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능
     //- 제목, 작성자명(username), 작성 내용을 저장하고
     //- 저장된 게시글을 Client 로 반환하기
     @PostMapping("/api/article")
     public ArticleResponseDto createArticle(@RequestBody ArticleRequestDto requestDto, HttpServletRequest request){
          return articleService.createArticle(requestDto, request);
     }
     // 선택한 게시글 조회 API
     // 선택한 게시글의 제목, 작성자명(username), 작성 날짜, 작성 내용을 조회하기
     //(검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
     @GetMapping("/api/article/{id}")
     public ArticleResponseDto getArticle(@PathVariable Long id){
          return articleService.getArticle(id);
     }
     
     // 선택한 게시글 수정
     //- 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
     //- 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
     @PutMapping("/api/article/{id}")
     public ResonseImpl<ArticleResponseDto, ResponseDto> updateArticle(@PathVariable Long id, @RequestBody ArticleRequestDto requestDto, HttpServletRequest request){
          return articleService.updateArticle(id, requestDto, request);
     }
     
     // 선택한 게시글 삭제
     //- 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 삭제 가능
     //- 선택한 게시글을 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
     @DeleteMapping("/api/article/{id}")
     public ResponseDto deleteArticle(@PathVariable Long id, HttpServletRequest request){
          return articleService.deleteArticle(id, request);
     }
}
