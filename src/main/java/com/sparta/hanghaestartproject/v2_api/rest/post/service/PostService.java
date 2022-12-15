package com.sparta.hanghaestartproject.v2_api.rest.post.service;

import com.sparta.hanghaestartproject.config.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.v1_api.rest.comment.model.Comment;
import com.sparta.hanghaestartproject.v1_api.rest.post.dto.PostRequestDto;
import com.sparta.hanghaestartproject.v1_api.rest.post.dto.PostResponseDto;
import com.sparta.hanghaestartproject.v1_api.rest.post.model.Post;
import com.sparta.hanghaestartproject.v1_api.rest.user.model.User;
import com.sparta.hanghaestartproject.config.model.UserRoleEnum;
import com.sparta.hanghaestartproject.config.errorcode.CommonErrorCode;
import com.sparta.hanghaestartproject.config.exception.RestApiException;
import com.sparta.hanghaestartproject.config.jwt.JwtUtil;
import com.sparta.hanghaestartproject.v1_api.rest.comment.repository.CommentRepository;
import com.sparta.hanghaestartproject.v1_api.rest.like.repository.LikePostRepository;
import com.sparta.hanghaestartproject.v1_api.rest.post.repository.PostRepository;
import com.sparta.hanghaestartproject.v1_api.rest.user.repository.UserRepository;
import com.sparta.hanghaestartproject.config.GetUser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
     private final GetUser getUser;
     private final PostRepository postRepository;
     private final CommentRepository commentRepository;
     private final UserRepository userRepository;
//     private final JwtUtil jwtUtil;

     private final LikePostRepository likePostRepository;
     
     PostService(GetUser getUser, PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository, JwtUtil jwtUtil, LikePostRepository likePostRepository){
          this.getUser = getUser;
          this.postRepository = postRepository;
          this.commentRepository = commentRepository;
          this.userRepository = userRepository;
//          this.jwtUtil = jwtUtil;
          this.likePostRepository = likePostRepository;
     }
     
     @Transactional (readOnly = true)
     public List<PostResponseDto> getPosts(int page, int size, String sortBy, boolean isAsc) {
          // 페이징 처리
          Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC; // Direction : Sort 안의 ENUM. ASC or DESC
          Sort sort = Sort.by(direction, sortBy);

          Pageable pageable = PageRequest.of(page, size, sort); // page : zero-based page index, size : the size of the page to be returned,

          return postRepository.findAllByOrderByCreatedAtDesc(pageable).stream()
               .map(PostResponseDto::new) // Post >> PostResponseDto 로 타입변환
               .collect(Collectors.toList()); // 다시 List로 묶은거
     }
     
     @Transactional
     public PostResponseDto createPost(PostRequestDto requestDto, User user) {

          Post post = new Post(requestDto, user.getUsername());

          post = postRepository.save(post);

          Long Sum = likePostRepository.countByPost(post);

          post.setLikePostNum(Sum + 0);

          return new PostResponseDto(post);
     }
     
     @Transactional (readOnly = true)
     public PostResponseDto getPost(Long id, int page, int size, String sortBy, boolean isAsc) {
          // 1. post를 먼저 찾고
          // 2. post에 저장된 likepost를 가져오고
          // 3. likePost의 유저 id를 활용해서 유저를 찾아오고
          // 4. user 정보를 리턴한다.

          // 페이징 처리
          Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC; // Direction : Sort 안의 ENUM. ASC or DESC
          Sort sort = Sort.by(direction, sortBy);
          Pageable pageable = PageRequest.of(page, size, sort); // page : zero-based page index, size : the size of the page to be returned,
          Post post = postRepository.findById(id)
               .orElseThrow(() -> new RestApiException(CommonErrorCode.NO_ARTICLE));

          int size1 = post.getLikePostList().size();


          List<Comment> commentList = commentRepository.findAllByPostId(id, pageable);
          post.setCommentList(commentList);

          return new PostResponseDto(post);
     }
     
     //- 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
     //- 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
     @Transactional
     public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
          Post post = postRepository.findById(id)
               .orElseThrow(() -> new RestApiException(CommonErrorCode.NO_ARTICLE));

          checkedUserRole(user, post);

          post.update(requestDto);
          return new PostResponseDto(post);
     }
     
     @Transactional
     public CompleteResponseDto deletePost(Long id, User user) {
          Post post = postRepository.findById(id)
               .orElseThrow(() -> new RestApiException(CommonErrorCode.NO_ARTICLE));

          checkedUserRole(user, post);

          postRepository.delete(post);
          return CompleteResponseDto.success("게시글 삭제 성공");
     }

     private static void checkedUserRole(User user, Post post) {
          if (user.getRole() == UserRoleEnum.USER) {
               if (!post.getUsername().equals(user.getUsername())) {
                    throw new RestApiException(CommonErrorCode.INVALID_USER);
               }
          }
     }

}

