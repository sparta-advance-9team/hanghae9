package com.sparta.hanghaestartproject.controller;

import com.sparta.hanghaestartproject.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.dto.ReCommentRequestDto;
import com.sparta.hanghaestartproject.dto.ReCommentResponseDto;
import com.sparta.hanghaestartproject.security.UserDetailsImpl;
import com.sparta.hanghaestartproject.service.ReCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/cocomment/{id}")
public class ReCommentController {

    private final ReCommentService reCommentService;

    public ReCommentController(ReCommentService reCommentService){this.reCommentService = reCommentService;}

    @PostMapping
    public ReCommentResponseDto createReComment(
            @PathVariable Long id,
            @RequestBody ReCommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("userDetails :" + userDetails);
        return reCommentService.createReComment(id, requestDto, userDetails.getUser());
    }


    @PutMapping
    public ReCommentResponseDto updateReComment(
            @PathVariable Long id,
            @RequestBody ReCommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return reCommentService.updateReComment(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping
    public CompleteResponseDto deleteReComment(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return reCommentService.deleteReComment(id, userDetails.getUser());
    }

}


