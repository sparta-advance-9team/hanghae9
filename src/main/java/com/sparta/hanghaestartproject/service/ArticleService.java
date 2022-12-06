package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.*;
import com.sparta.hanghaestartproject.entity.Article;
import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.entity.UserRoleEnum;
import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.ArticleRepository;
import com.sparta.hanghaestartproject.repository.UserRepository;
import com.sparta.hanghaestartproject.util.GetUser;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
     private final GetUser getUser;
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
     public ResponseImpl<ArticleResponseDto, ResponseDto> createArticle(ArticleRequestDto requestDto, HttpServletRequest request) {
          User user = getUser.getUser(request);
          if (user == null) return ResponseDto.fail(getUser.msg);
          // 토큰이 있는 경우에만 관심상품 추가 가능
          Article article = new Article(requestDto, user.getUsername()); // ArticleRequestDto >> Article
          article = articleRepository.save(article);
          article.setUser(user);
          return new ArticleResponseDto(article);
     }
     
     @Transactional (readOnly = true)
     public ResponseImpl<ArticleResponseDto, ResponseDto> getArticle(Long id) {
          Article article = articleRepository.findById(id).get();
          if(article==null) return ResponseDto.fail("게시글이 존재하지 않습니다.");
          return new ArticleResponseDto(article);
     }
     
     //- 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
     //- 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
     @Transactional
     public ResponseImpl updateArticle(Long id, ArticleRequestDto requestDto, HttpServletRequest request) {
          User user = getUser.getUser(request);
          if (user == null) return ResponseDto.fail(getUser.msg);
          // 토큰이 있는 경우에만 관심상품 추가 가능
          
          Article article = articleRepository.findById(id).orElseGet(() -> null);
          
          if(article == null) return ResponseDto.fail("게시글이 존재하지 않습니다.");
          if(user.getRole() == UserRoleEnum.USER) {
               if (!article.getUsername().equals(user.getUsername())) {
                    return ResponseDto.fail("작성자만 삭제/수정할 수 있습니다.");
               }
          }
          article.update(requestDto);
          articleRepository.save(article);
     
          return new ArticleResponseDto(article);
     }
     
     @Transactional
     public ResponseDto deleteArticle(Long id, HttpServletRequest request) {
          User user = getUser.getUser(request);
          // 토큰이 있는 경우에만 관심상품 추가 가능
          if (user == null) return ResponseDto.fail(getUser.msg);
          
          Article article = articleRepository.findById(id).orElseGet(() -> null);
          if(article == null) return ResponseDto.fail("게시글이 존재하지 않습니다.");
          if(user.getRole() == UserRoleEnum.USER){
               if(!article.getUsername().equals(user.getUsername())){
                    return ResponseDto.fail("작성자만 삭제/수정할 수 있습니다.");
               }
          }
          articleRepository.delete(article);
          return ResponseDto.success("게시글 삭제 성공");
     }
     
}

