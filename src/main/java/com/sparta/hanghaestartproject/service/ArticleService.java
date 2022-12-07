package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.*;
import com.sparta.hanghaestartproject.entity.Article;
import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.entity.UserRoleEnum;
import com.sparta.hanghaestartproject.errorcode.CommonErrorCode;
import com.sparta.hanghaestartproject.exception.RestApiException;
import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.ArticleRepository;
import com.sparta.hanghaestartproject.repository.UserRepository;
import com.sparta.hanghaestartproject.util.GetUser;
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
     public ArticleResponseDto createArticle(ArticleRequestDto requestDto, HttpServletRequest request) {
          User user = getUser.getUser(request);
          // 토큰이 있는 경우에만 관심상품 추가 가능
          Article article = new Article(requestDto, user.getUsername());
          article = articleRepository.save(article);
          article.setUser(user);
          return new ArticleResponseDto(article);
     }
     
     @Transactional (readOnly = true)
     public ArticleResponseDto getArticle(Long id) {
          Article article = articleRepository.findById(id)
               .orElseThrow(() -> new RestApiException(CommonErrorCode.NO_ARTICLE));
          
          return new ArticleResponseDto(article);
          
     }
     
     //- 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
     //- 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
     @Transactional
     public ArticleResponseDto updateArticle(Long id, ArticleRequestDto requestDto, HttpServletRequest request) {
          User user = getUser.getUser(request);
          Article article = articleRepository.findById(id)
               .orElseThrow(() -> new RestApiException(CommonErrorCode.NO_ARTICLE));
          
          if (user.getRole() == UserRoleEnum.USER) {
               if (!article.getUsername().equals(user.getUsername())) {
                    throw new RestApiException(CommonErrorCode.INVALID_USER);
               }
          }
          article.update(requestDto);
          return new ArticleResponseDto(article);
     }
     
     @Transactional
     public CompleteResponseDto deleteArticle(Long id, HttpServletRequest request) {
          User user = getUser.getUser(request);
          Article article = articleRepository.findById(id)
               .orElseThrow(() -> new RestApiException(CommonErrorCode.NO_ARTICLE));
          
          if (user.getRole() == UserRoleEnum.USER) {
               if (!article.getUsername().equals(user.getUsername())) {
                    throw new RestApiException(CommonErrorCode.INVALID_USER);
               }
          }
          articleRepository.delete(article);
          return CompleteResponseDto.success("게시글 삭제 성공");
     }
     
}

