package com.sparta.hanghaestartproject.v1_api.rest.comment.service;

import com.sparta.hanghaestartproject.config.model.UserRoleEnum;
import com.sparta.hanghaestartproject.config.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.v1_api.rest.comment.dto.ReCommentRequestDto;
import com.sparta.hanghaestartproject.v1_api.rest.comment.dto.ReCommentResponseDto;
import com.sparta.hanghaestartproject.config.errorcode.CommonErrorCode;
import com.sparta.hanghaestartproject.config.exception.RestApiException;
import com.sparta.hanghaestartproject.config.jwt.JwtUtil;
import com.sparta.hanghaestartproject.v1_api.rest.comment.repository.CommentRepository;
import com.sparta.hanghaestartproject.v1_api.rest.post.repository.PostRepository;
import com.sparta.hanghaestartproject.v1_api.rest.comment.repository.ReCommentRepository;
import com.sparta.hanghaestartproject.v1_api.rest.comment.model.ReComment;
import com.sparta.hanghaestartproject.v1_api.rest.user.repository.UserRepository;
import com.sparta.hanghaestartproject.config.GetUser;
import com.sparta.hanghaestartproject.v1_api.rest.comment.model.Comment;
import com.sparta.hanghaestartproject.v1_api.rest.user.model.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReCommentService {

    private final GetUser getUser;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReCommentRepository reCommentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public ReCommentService(GetUser getUser, PostRepository postRepository, CommentRepository commentRepository, ReCommentRepository reCommentRepository, UserRepository userRepository, JwtUtil jwtUtil){
        this.getUser = getUser;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.reCommentRepository = reCommentRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
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
        reCommentRepository.delete(recomment);x
        return CompleteResponseDto.success("대댓글 삭제 성공");

    }



}
