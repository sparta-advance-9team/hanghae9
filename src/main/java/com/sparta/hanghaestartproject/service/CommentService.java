package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.CommentRequestDto;
import com.sparta.hanghaestartproject.dto.CommentResponseDto;
import com.sparta.hanghaestartproject.dto.ResponseDto;
import com.sparta.hanghaestartproject.dto.ResponseImpl;
import com.sparta.hanghaestartproject.entity.Comment;
import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.entity.UserRoleEnum;
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
     public ResponseImpl<CommentResponseDto, ResponseDto> createComment
          (Long id, CommentRequestDto requestDto, HttpServletRequest request) {
          User user = getUser.getUser(request);
          if (user == null) return ResponseDto.fail(getUser.msg);
          if(articleRepository.existsById(id)){
               Comment comment = new Comment(requestDto, user.getUsername());
               comment.setArticle(articleRepository.findById(id).get());
               commentRepository.save(comment);
               return new CommentResponseDto(comment);
          }else{
               return ResponseDto.fail("게시글이 존재하지 않습니다.");
//               return ResponseDto.fail("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
          }
     }
     @Transactional
     public ResponseImpl<CommentResponseDto, ResponseDto> updateComment
          (Long id, CommentRequestDto requestDto, HttpServletRequest request) {
          User user = getUser.getUser(request);
          if (user == null) return ResponseDto.fail(getUser.msg);
          if(!commentRepository.existsById(id)){
               return ResponseDto.fail("댓글이 존재하지 않습니다.");
          }
          Comment comment = commentRepository.findById(id).get();
          if(user.getRole().equals(UserRoleEnum.USER)){
               if(!comment.getUsername().equals(user.getUsername())){
                    return ResponseDto.fail("작성자만 삭제/수정할 수 있습니다.");
               }
          }
          comment.update(requestDto);
          return new CommentResponseDto(comment);
     }
     public ResponseDto deleteComment
          (Long id, CommentRequestDto requestDto, HttpServletRequest request) {
          User user = getUser.getUser(request);
          if (user == null) return ResponseDto.fail(getUser.msg);
          if(!commentRepository.existsById(id)){
               return ResponseDto.fail("댓글이 존재하지 않습니다.");
          }
          Comment comment = commentRepository.findById(id).get();
          if(user.getRole().equals(UserRoleEnum.USER)){
               if(!comment.getUsername().equals(user.getUsername())){
                    return ResponseDto.fail("작성자만 삭제/수정할 수 있습니다.");
               }
          }
          commentRepository.delete(comment);
          return ResponseDto.success("댓글 삭제 성공");
     }
     
}
