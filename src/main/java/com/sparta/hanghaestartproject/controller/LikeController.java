package com.sparta.hanghaestartproject.controller;

import com.sparta.hanghaestartproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

     private final LikeService likeService;

//     @PutMapping ("/api/like/post/{id}")
//     @PutMapping ("/api/like/comment/{id}")

     @PostMapping("api/like/post/{id}")  // post id
     public CompleteResponseDto likePost(@PathVariable Long id,HttpServletRequest request) {
          return likeService.likePost(id, request);
     }

     @PostMapping("api/like/comment/{id}")   //comment id
     public CompleteResponseDto likeComment(@PathVariable Long id, HttpServletRequest request) {
          return likeService.likeComment(id, request);
     }

}
