package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.*;
import com.sparta.hanghaestartproject.entity.Post;
import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.entity.UserRoleEnum;
import com.sparta.hanghaestartproject.errorcode.CommonErrorCode;
import com.sparta.hanghaestartproject.exception.RestApiException;
import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.PostRepository;
import com.sparta.hanghaestartproject.repository.UserRepository;
import com.sparta.hanghaestartproject.util.GetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
     private final GetUser getUser;
     private final PostRepository postRepository;
     private final UserRepository userRepository;
     private final JwtUtil jwtUtil;
     
     @Transactional (readOnly = true)
     public List<PostResponseDto> getPosts() {
          return postRepository.findAllByOrderByCreatedAtDesc().stream()
               .map(PostResponseDto::new) // Post >> PostResponseDto 로 타입변환
               .collect(Collectors.toList()); // 다시 List로 묶은거
     }
     
     @Transactional
     public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {
          User user = getUser.getUser(request);
          // 토큰이 있는 경우에만 관심상품 추가 가능
          Post post = new Post(requestDto, user.getUsername());
          post = postRepository.save(post);
          return new PostResponseDto(post);
     }
     
     @Transactional (readOnly = true)
     public PostResponseDto getPost(Long id) {
          Post post = postRepository.findById(id)
               .orElseThrow(() -> new RestApiException(CommonErrorCode.NO_ARTICLE));
          
          return new PostResponseDto(post);
     }
     
     //- 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
     //- 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
     @Transactional
     public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
          User user = getUser.getUser(request);
          Post post = postRepository.findById(id)
               .orElseThrow(() -> new RestApiException(CommonErrorCode.NO_ARTICLE));
          
          if (user.getRole() == UserRoleEnum.USER) {
               if (!post.getUsername().equals(user.getUsername())) {
                    throw new RestApiException(CommonErrorCode.INVALID_USER);
               }
          }
          post.update(requestDto);
          return new PostResponseDto(post);
     }
     
     @Transactional
     public CompleteResponseDto deletePost(Long id, HttpServletRequest request) {
          User user = getUser.getUser(request);
          Post post = postRepository.findById(id)
               .orElseThrow(() -> new RestApiException(CommonErrorCode.NO_ARTICLE));
          
          if (user.getRole() == UserRoleEnum.USER) {
               if (!post.getUsername().equals(user.getUsername())) {
                    throw new RestApiException(CommonErrorCode.INVALID_USER);
               }
          }
          postRepository.delete(post);
          return CompleteResponseDto.success("게시글 삭제 성공");
     }
     
}

