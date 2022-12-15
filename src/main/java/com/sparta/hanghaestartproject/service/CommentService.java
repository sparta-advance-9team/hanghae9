package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.CommentRequestDto;
import com.sparta.hanghaestartproject.dto.CommentResponseDto;
import com.sparta.hanghaestartproject.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.entity.Post;
import com.sparta.hanghaestartproject.entity.Comment;
import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.entity.UserRoleEnum;
import com.sparta.hanghaestartproject.errorcode.CommonErrorCode;
import com.sparta.hanghaestartproject.exception.RestApiException;
import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.LikeCommentRepository;
import com.sparta.hanghaestartproject.repository.PostRepository;
import com.sparta.hanghaestartproject.repository.CommentRepository;
import com.sparta.hanghaestartproject.repository.UserRepository;
import com.sparta.hanghaestartproject.util.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
     private final PostRepository postRepository;
     private final CommentRepository commentRepository;
     private final UserRepository userRepository;
     private final JwtUtil jwtUtil;

     private final LikeCommentRepository likeCommentRepository;
     
     public CommentService( PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository, JwtUtil jwtUtil, LikeCommentRepository likeCommentRepository){
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
     
          Util.checkCommentUsernameByUser(user, comment);
          comment.update(requestDto);
          return new CommentResponseDto(comment);
     }
     
     public CompleteResponseDto deleteComment
          (Long id, User user) {
          Comment comment = commentRepository.findById(id)
               .orElseThrow(()-> new RestApiException(CommonErrorCode.NO_COMMENT));
     
          Util.checkCommentUsernameByUser(user, comment);
          commentRepository.delete(comment);
          return CompleteResponseDto.success("댓글 삭제 성공");
     }
     
     
}
