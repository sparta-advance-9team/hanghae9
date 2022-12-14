package com.sparta.hanghaestartproject.controller;

import com.sparta.hanghaestartproject.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.security.UserDetailsImpl;
import com.sparta.hanghaestartproject.service.LikeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
//@RequiredArgsConstructor
public class LikeController {

     private final LikeService likeService;

     public LikeController(LikeService likeService) {
          this.likeService = likeService;
     }

     @PostMapping("api/like/post/{id}")  // post id
     public CompleteResponseDto likePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
          return likeService.likePost(id, userDetails.getUser());
     }

     @PostMapping("api/like/comment/{id}")   //comment id
     public CompleteResponseDto likeComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
          return likeService.likeComment(id, userDetails.getUser());
     }

}
