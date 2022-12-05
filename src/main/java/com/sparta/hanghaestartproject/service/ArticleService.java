package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.ArticleRequestDto;
import com.sparta.hanghaestartproject.dto.ArticleResponseDto;
import com.sparta.hanghaestartproject.entity.Article;
import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.ArticleRepository;
import com.sparta.hanghaestartproject.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
     
     private final ArticleRepository articleRepository;
     private final UserRepository userRepository;
     private final JwtUtil jwtUtil;
     
     @Transactional (readOnly = true)
     public List<ArticleResponseDto> getArticles() {
          return articleRepository.findAllByOrderByCreatedAtDesc().stream()
               .map(ArticleResponseDto::new) // Article >> ArticleResponseDto 로 타입변환
               .collect(Collectors.toList()); // 다시 List로 묶은거
     }
     
     @Transactional
     public ArticleResponseDto createArticle(ArticleRequestDto requestDto, HttpServletRequest request) {
          User user = getUser(request);
          if (user == null) throw new IllegalArgumentException("Token 이 존재하지 않습니다.");
          // 토큰이 있는 경우에만 관심상품 추가 가능
          Article article = new Article(requestDto, user.getUsername()); // ArticleRequestDto >> Article
          article = articleRepository.save(article);
          article.setUser(user);
          return new ArticleResponseDto(article);
     }
     
     @Transactional (readOnly = true)
     public ArticleResponseDto getArticle(Long id) {
          Article article = articleRepository.findById(id).orElseThrow(
               () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
          );
          return new ArticleResponseDto(article);
     }
     
     //- 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
     //- 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
     @Transactional
     public ArticleResponseDto updateArticle(Long id, ArticleRequestDto requestDto, HttpServletRequest request) {
          User user = getUser(request);
          if (user == null) throw new IllegalArgumentException("Token 이 존재하지 않습니다.");
          // 토큰이 있는 경우에만 관심상품 추가 가능
          
          Article article = articleRepository.findById(id).orElseThrow(
               () -> new IllegalArgumentException("존재하지 않는 글입니다.")
          );
          article.update(requestDto);
          articleRepository.save(article);
          
          return new ArticleResponseDto(article);
     }
     
     @Transactional
     public boolean deleteArticle(Long id, HttpServletRequest request) {
          User user = getUser(request);
          // 토큰이 있는 경우에만 관심상품 추가 가능
          if (user == null) throw new IllegalArgumentException("Token 이 존재하지 않습니다.");
          
          if (articleRepository.existsByIdAndUsername(id, user.getUsername())) {
               articleRepository.deleteById(id);
               return true;
          }
          return false;
     }
     
     private User getUser(HttpServletRequest request) {
          // Request에서 Token 가져오기
          String token = jwtUtil.resolveToken(request);
          Claims claims;
          
          // 토큰이 있는 경우에만 관심상품 추가 가능
          if (token != null) {
               if (jwtUtil.validateToken(token)) {
                    // 토큰에서 사용자 정보 가져오기
                    claims = jwtUtil.getUserInfoFromToken(token);
               } else {
                    throw new IllegalArgumentException("Token Error");
               }
               
               // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
               User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
               );
               return user;
          } else {
               throw new IllegalArgumentException("Token 이 존재하지 않습니다.");
          }
     }
}

