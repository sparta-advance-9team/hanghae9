package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.LikeCommentRepository;
import com.sparta.hanghaestartproject.repository.LikePostRepository;
import com.sparta.hanghaestartproject.util.GetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
     private final GetUser getUser;
     private final LikePostRepository likePostRepository;
     private final LikeCommentRepository likeCommentRepository;
     private final JwtUtil jwtUtil;
     
     
}
