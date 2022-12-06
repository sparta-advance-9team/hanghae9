package com.sparta.hanghaestartproject.util;

import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class GetUser {
     
     public static String msg;
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
               } else {
                    msg = "토큰이 유효하지 않습니다.";
                    return null;
               }
               // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
               User user = userRepository.findByUsername(claims.getSubject()).orElseGet(()->null);
               if(user==null){
                    msg = "회원을 찾을 수 없습니다.";
                    return null;
               }
               return user;
          } else {
               msg = "토큰이 유효하지 않습니다.";
               return null;
          }
     }
}
