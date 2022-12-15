package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.dto.ReCommentRequestDto;
import com.sparta.hanghaestartproject.dto.ReCommentResponseDto;
import com.sparta.hanghaestartproject.entity.*;
import com.sparta.hanghaestartproject.errorcode.CommonErrorCode;
import com.sparta.hanghaestartproject.exception.RestApiException;
import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.CommentRepository;
import com.sparta.hanghaestartproject.repository.PostRepository;
import com.sparta.hanghaestartproject.repository.ReCommentRepository;
import com.sparta.hanghaestartproject.repository.UserRepository;
import com.sparta.hanghaestartproject.util.Util;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReCommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReCommentRepository reCommentRepository;
    private final UserRepository userRepository;


    public ReCommentService(PostRepository postRepository, CommentRepository commentRepository, ReCommentRepository reCommentRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.reCommentRepository = reCommentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ReCommentResponseDto createReComment(Long id, ReCommentRequestDto requestDto, User user){

        Comment comment = commentRepository.findById(id).orElseThrow(()-> new RestApiException(CommonErrorCode.NO_ARTICLE));

        ReComment recomment = new ReComment(requestDto, user.getUsername());
        recomment.updateComment(comment);
        reCommentRepository.save(recomment);
        return new ReCommentResponseDto(recomment);

    }


    public ReCommentResponseDto updateReComment(Long id, ReCommentRequestDto requestDto, User user) {
        ReComment recomment = reCommentRepository.findById(id)
                .orElseThrow(()-> new RestApiException(CommonErrorCode.NO_COMMENT));

        if(user.getRole().equals(UserRoleEnum.USER)){
            if(!recomment.getUsername().equals(user.getUsername())){
                throw new RestApiException((CommonErrorCode.INVALID_USER));
            }
        }
        recomment.update(requestDto);
        return new ReCommentResponseDto(recomment);
    }


    public CompleteResponseDto deleteReComment(Long id, User user){
        ReComment recomment = reCommentRepository.findById(id).orElseThrow(()-> new RestApiException(CommonErrorCode.NO_COMMENT));

        if(user.getRole().equals(UserRoleEnum.USER)){
            if (!recomment.getUsername().equals(user.getUsername())) {
                throw new RestApiException(CommonErrorCode.INVALID_USER);
            }
        }
        reCommentRepository.delete(recomment);
        return CompleteResponseDto.success("대댓글 삭제 성공");

    }



}
