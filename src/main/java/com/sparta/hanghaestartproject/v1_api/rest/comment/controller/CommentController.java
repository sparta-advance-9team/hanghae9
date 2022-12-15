package com.sparta.hanghaestartproject.v1_api.rest.comment.controller;

import com.sparta.hanghaestartproject.v1_api.rest.comment.dto.CommentRequestDto;
import com.sparta.hanghaestartproject.v1_api.rest.comment.dto.CommentResponseDto;
import com.sparta.hanghaestartproject.config.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.config.security.UserDetailsImpl;
import com.sparta.hanghaestartproject.v1_api.rest.comment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment/{postId}")
    public CommentResponseDto createComment(@PathVariable Long id, // article Id
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        log.info("userDetails : " + userDetails);
        return commentService.createComment(id, requestDto, userDetails.getUser());
    }

    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(
            @PathVariable Long id, // commentId
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/comment/{id}")
    public CompleteResponseDto deleteComment(
            @PathVariable Long id, // commentId
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails.getUser());
    }

    //    @Secured(value = UserRoleEnum.Authority.ADMIN) //Admin 전용
    //    @PostMapping("/test-secured")
    //    public String securedTest(@AuthenticationPrincipal UserDetailsImpl userDetails) {

}
