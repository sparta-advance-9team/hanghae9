package com.sparta.hanghaestartproject.controller;

import com.sparta.hanghaestartproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {
     
     private final LikeService likeService;
     
//     @PutMapping ("/api/like/post/{id}")
//     @PutMapping ("/api/like/comment/{id}")

}
