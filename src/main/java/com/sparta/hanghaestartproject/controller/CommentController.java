package com.sparta.hanghaestartproject.controller;

import com.sparta.hanghaestartproject.dto.CommentRequestDto;
import com.sparta.hanghaestartproject.dto.CommentResponseDto;
import com.sparta.hanghaestartproject.dto.ResponseImpl;
import com.sparta.hanghaestartproject.dto.ResponseDto;
import com.sparta.hanghaestartproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {
     private final CommentService commentService;
     
     @PostMapping("/api/comment/{id}")
     public ResponseImpl<CommentResponseDto, ResponseDto> createComment(
               @PathVariable Long id, // article Id
               @RequestBody CommentRequestDto requestDto,
               HttpServletRequest request){
          return commentService.createComment(id,requestDto, request);
     }
     
     @PutMapping("/api/comment/{id}")
     public ResponseImpl<CommentResponseDto, ResponseDto> updateComment(
          @PathVariable Long id, // commentId
          @RequestBody CommentRequestDto requestDto,
          HttpServletRequest request){
          return commentService.updateComment(id, requestDto, request);
     }
     
     @DeleteMapping("/api/comment/{id}")
     public ResponseDto deleteComment(
          @PathVariable Long id, // commentId
          @RequestBody CommentRequestDto requestDto,
          HttpServletRequest request){
          return commentService.deleteComment(id, requestDto, request);
     }
     
     
}
