package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.entity.*;
import com.sparta.hanghaestartproject.errorcode.CommonErrorCode;
import com.sparta.hanghaestartproject.exception.RestApiException;
import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.*;
import com.sparta.hanghaestartproject.util.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@RequiredArgsConstructor
public class LikeService {
     private final LikePostRepository likePostRepository;
     private final LikeCommentRepository likeCommentRepository;
     private final PostRepository postRepository;
     private final CommentRepository commentRepository;

     public LikeService(LikePostRepository likePostRepository, LikeCommentRepository likeCommentRepository, PostRepository postRepository, CommentRepository commentRepository) {
          this.likePostRepository = likePostRepository;
          this.likeCommentRepository = likeCommentRepository;
          this.postRepository = postRepository;
          this.commentRepository = commentRepository;
     }

     @Transactional
     public CompleteResponseDto likePost(Long id, User user) {
          Post post = postRepository.findById(id)
                  .orElseThrow(() ->new RestApiException(CommonErrorCode.NO_ARTICLE));
              // security 이후 추가할듯 아마?

          if (likePostRepository.findByPostAndUser(post, user) == null) {
               // 좋아요 안눌렀으면 likePost 만들고 좋아요처리
               LikePost likePost = new LikePost(post, user);
               likePostRepository.save(likePost);
//               Long Sum = likePostRepository.countByPost(post);
               post.setLikePostNum(post.getLikePostNum() + 1);
               return CompleteResponseDto.success("따봉 추가");
          } else {
               // 좋아요 누른상태면 취소처리후 테이블 삭제
               LikePost likePost = likePostRepository.findByPostAndUser(post, user);
               likePost.unLikePost(post);
               likePostRepository.delete(likePost);
//               Long Sum = likePostRepository.countByPost(post);
               post.setLikePostNum(post.getLikePostNum() - 1);
               return CompleteResponseDto.success("따봉 취소");
          }


     }

     @Transactional
     public CompleteResponseDto likeComment(Long id, User user) {
          Comment comment = commentRepository.findById(id)
                  .orElseThrow(() ->new RestApiException(CommonErrorCode.NO_COMMENT));
                // security 이후 추가할듯 아마?

          if (likeCommentRepository.findByCommentAndUser(comment, user) == null) {
               // 좋아요 안눌렀으면 likeComment 만들고 좋아요처리
               LikeComment likeComment = new LikeComment(comment, user);
               likeCommentRepository.save(likeComment);
//               Long sum = likeCommentRepository.countByComment(comment);
               comment.setLikeCommentNum(comment.getLikeCommentNum() + 1);
               return CompleteResponseDto.success("따봉 추가");
          } else {
               // 좋아요 누른상태면 취소처리후 테이블 삭제
               LikeComment likeComment = likeCommentRepository.findByCommentAndUser(comment, user);
               likeComment.unLikeComment(comment);
               likeCommentRepository.delete(likeComment);
//               Long sum = likeCommentRepository.countByComment(comment);
               comment.setLikeCommentNum(comment.getLikeCommentNum() - 1);
               return CompleteResponseDto.success("따봉 취소");
          }
     }

}
