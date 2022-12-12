package com.sparta.hanghaestartproject.controller;

import com.sparta.hanghaestartproject.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class LikeController {
     
     private final LikeService likeService;

}
