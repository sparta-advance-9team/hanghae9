package com.sparta.hanghaestartproject.v2_api.rest.comment.service;

import com.sparta.hanghaestartproject.v1_api.rest.comment.dto.CommentRequestDto;
import com.sparta.hanghaestartproject.v1_api.rest.comment.dto.CommentResponseDto;
import com.sparta.hanghaestartproject.config.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.v1_api.rest.post.model.Post;
import com.sparta.hanghaestartproject.v1_api.rest.comment.model.Comment;
import com.sparta.hanghaestartproject.v1_api.rest.user.model.User;
import com.sparta.hanghaestartproject.config.model.UserRoleEnum;
import com.sparta.hanghaestartproject.config.errorcode.CommonErrorCode;
import com.sparta.hanghaestartproject.config.exception.RestApiException;
import com.sparta.hanghaestartproject.config.jwt.JwtUtil;
import com.sparta.hanghaestartproject.v1_api.rest.like.repository.LikeCommentRepository;
import com.sparta.hanghaestartproject.v1_api.rest.post.repository.PostRepository;
import com.sparta.hanghaestartproject.v1_api.rest.comment.repository.CommentRepository;
import com.sparta.hanghaestartproject.v1_api.rest.user.repository.UserRepository;
import com.sparta.hanghaestartproject.config.GetUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
     private final GetUser getUser;
     private final PostRepository postRepository;
     private final CommentRepository commentRepository;
     private final UserRepository userRepository;
     private final JwtUtil jwtUtil;

     private final LikeCommentRepository likeCommentRepository;
     
     public CommentService(GetUser getUser, PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository, JwtUtil jwtUtil, LikeCommentRepository likeCommentRepository){
          this.getUser = getUser;
          this.postRepository = postRepository;
          this.commentRepository = commentRepository;
          this.userRepository = userRepository;
          this.jwtUtil = jwtUtil;
          this.likeCommentRepository = likeCommentRepository;
     }
     
     @Transactional
     public CommentResponseDto createComment // id : 게시글 id
          (Long id, CommentRequestDto requestDto, User user) {

          Post post = postRepository.findById(id)
               .orElseThrow(() -> new RestApiException(CommonErrorCode.NO_ARTICLE));
          
          Comment comment = new Comment(requestDto, user.getUsername());
          comment.updatePost(post);
          commentRepository.save(comment);
          Long sum = likeCommentRepository.countByComment(comment);
          comment.setLikeCommentNum(sum + 0);
          return new CommentResponseDto(comment);
     }
     
     @Transactional
     public CommentResponseDto updateComment
          (Long id, CommentRequestDto requestDto, User user) {
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
          (Long id, User user) {
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
