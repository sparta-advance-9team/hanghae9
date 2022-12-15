package com.sparta.hanghaestartproject.util;

import com.sparta.hanghaestartproject.entity.Comment;
import com.sparta.hanghaestartproject.entity.Post;
import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.entity.UserRoleEnum;
import com.sparta.hanghaestartproject.errorcode.CommonErrorCode;
import com.sparta.hanghaestartproject.errorcode.UserErrorCode;
import com.sparta.hanghaestartproject.exception.RestApiException;
import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class Util {
     private final UserRepository userRepository;
     private final JwtUtil jwtUtil;
     
     public User getUser(HttpServletRequest request) {
          // Request에서 Token 가져오기
          String token = jwtUtil.resolveToken(request);
          Claims claims;
          
          // 토큰이 있는 경우에만 관심상품 추가 가능
          if (token != null) {
               if (jwtUtil.validateToken(token)) {
                    // 토큰에서 사용자 정보 가져오기
                    claims = jwtUtil.getUserInfoFromToken(token);
                    System.out.println(">>>>>>>>>>>>>>" + claims.get("auth"));
               } else {
                    throw new RestApiException(UserErrorCode.INVALID_TOKEN);
               }
               // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
               User user = userRepository.findByUsername(claims.getSubject())
                    .orElseThrow(() -> new RestApiException(UserErrorCode.NO_USER));
               
               return user;
          } else {
               throw new RestApiException(UserErrorCode.INVALID_TOKEN);
          }
     }
     
     public static void checkPostUsernameByUser(User user, Post post) {
          if (user.getRole() == UserRoleEnum.USER) {
               if (!post.getUsername().equals(user.getUsername())) {
                    throw new RestApiException(CommonErrorCode.INVALID_USER);
               }
          }
     }
     public static void checkCommentUsernameByUser(User user, Comment comment) {
          if (user.getRole().equals(UserRoleEnum.USER)) {
               if (!comment.getUsername().equals(user.getUsername())) {
                    throw new RestApiException(CommonErrorCode.INVALID_USER);
               }
          }
     }
     
}
