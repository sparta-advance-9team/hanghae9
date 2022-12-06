package com.sparta.hanghaestartproject.controller;

import com.sparta.hanghaestartproject.dto.CommentRequestDto;
import com.sparta.hanghaestartproject.dto.CommentResponseDto;
import com.sparta.hanghaestartproject.dto.ResonseImpl;
import com.sparta.hanghaestartproject.dto.ResponseDto;
import com.sparta.hanghaestartproject.service.ArticleService;
import com.sparta.hanghaestartproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {
     private final CommentService commentService;
     
     @PostMapping("/api/comment/{id}")
     public ResonseImpl<CommentResponseDto, ResponseDto> createComment(
               @PathVariable Long id,
               @RequestBody CommentRequestDto requestDto,
               HttpServletRequest request){
          return commentService.createComment(id,requestDto, request);
     }
     
     
}
