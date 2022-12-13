package com.sparta.hanghaestartproject.controller;

import com.sparta.hanghaestartproject.dto.CommentRequestDto;
import com.sparta.hanghaestartproject.dto.CommentResponseDto;
import com.sparta.hanghaestartproject.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.security.UserDetailsImpl;
import com.sparta.hanghaestartproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api")
public class CommentController {
     
     private final CommentService commentService;
     
     public CommentController(CommentService commentService) {
          this.commentService = commentService;
     }
     
     @PostMapping ("/comment/{id}")
     public CommentResponseDto createComment(
          @PathVariable Long id, // article Id
          @RequestBody CommentRequestDto requestDto,
          @AuthenticationPrincipal UserDetailsImpl userDetails) {
          log.info("userDetails : " + userDetails);
          return commentService.createComment(id, requestDto, userDetails.getUser());
     }
     
     @PutMapping ("/comment/{id}")
     public CommentResponseDto updateComment(
          @PathVariable Long id, // commentId
          @RequestBody CommentRequestDto requestDto,
          @AuthenticationPrincipal UserDetailsImpl userDetails) {
          return commentService.updateComment(id, requestDto, userDetails.getUser());
     }
     
     @DeleteMapping ("/comment/{id}")
     public CompleteResponseDto deleteComment(
          @PathVariable Long id, // commentId
          @AuthenticationPrincipal UserDetailsImpl userDetails) {
          return commentService.deleteComment(id, userDetails.getUser());
     }
     
     //    @Secured(value = UserRoleEnum.Authority.ADMIN) //Admin 전용
     //    @PostMapping("/test-secured")
     //    public String securedTest(@AuthenticationPrincipal UserDetailsImpl userDetails) {
     
}
