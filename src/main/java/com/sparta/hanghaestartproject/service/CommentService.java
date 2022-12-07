package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.CommentRequestDto;
import com.sparta.hanghaestartproject.dto.CommentResponseDto;
import com.sparta.hanghaestartproject.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.entity.Article;
import com.sparta.hanghaestartproject.entity.Comment;
import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.entity.UserRoleEnum;
import com.sparta.hanghaestartproject.errorcode.CommonErrorCode;
import com.sparta.hanghaestartproject.exception.RestApiException;
import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.ArticleRepository;
import com.sparta.hanghaestartproject.repository.CommentRepository;
import com.sparta.hanghaestartproject.repository.UserRepository;
import com.sparta.hanghaestartproject.util.GetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
     
     private final GetUser getUser;
     private final ArticleRepository articleRepository;
     private final CommentRepository commentRepository;
     private final UserRepository userRepository;
     private final JwtUtil jwtUtil;
     
     @Transactional
     public CommentResponseDto createComment // id : 게시글 id
          (Long id, CommentRequestDto requestDto, HttpServletRequest request) {
          User user = getUser.getUser(request);
          Article article =articleRepository.findById(id)
               .orElseThrow(() -> new RestApiException(CommonErrorCode.NO_ARTICLE));
          
          Comment comment = new Comment(requestDto, user.getUsername());
          comment.updateArticle(article);
          commentRepository.save(comment);
          return new CommentResponseDto(comment);
     }
     
     @Transactional
     public CommentResponseDto updateComment
          (Long id, CommentRequestDto requestDto, HttpServletRequest request) {
          User user = getUser.getUser(request);
          Comment comment = commentRepository.findById(id)
               .orElseThrow(()-> new RestApiException(CommonErrorCode.NO_COMMENT));
          
          if (user.getRole().equals(UserRoleEnum.USER)) {
               if (!comment.getUsername().equals(user.getUsername())) {
                    throw new RestApiException(CommonErrorCode.INVALID_USER);
               }
          }
          comment.update(requestDto);
          return new CommentResponseDto(comment);
     }
     
     public CompleteResponseDto deleteComment
          (Long id, CommentRequestDto requestDto, HttpServletRequest request) {
          User user = getUser.getUser(request);
          
          Comment comment = commentRepository.findById(id)
               .orElseThrow(()-> new RestApiException(CommonErrorCode.NO_COMMENT));
          
          if (user.getRole().equals(UserRoleEnum.USER)) {
               if (!comment.getUsername().equals(user.getUsername())) {
                    throw new RestApiException(CommonErrorCode.INVALID_USER);
               }
          }
          commentRepository.delete(comment);
          return CompleteResponseDto.success("댓글 삭제 성공");
     }
     
}
