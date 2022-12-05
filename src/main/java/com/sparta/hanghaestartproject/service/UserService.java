package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.LoginRequestDto;
import com.sparta.hanghaestartproject.dto.SignupRequestDto;
import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
     
     private final UserRepository userRepository;
     private final JwtUtil jwtUtil;
     public void signup(SignupRequestDto signupRequestDto) {
          String username = signupRequestDto.getUsername();
          String password = signupRequestDto.getPassword();
     
          // 회원 중복 확인
          Optional<User> found = userRepository.findByUsername(username);
          if (found.isPresent()) {
               throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
          }
          User user = new User(username, password);
     
          userRepository.save(user);
     }
     
     public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
          String username = loginRequestDto.getUsername();
          String password = loginRequestDto.getPassword();
     
          // 사용자 확인
          User user = userRepository.findByUsername(username).orElseThrow(
               () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
          );
          // 비밀번호 확인
          if(!user.getPassword().equals(password)){
               throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
          }
     
          response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
     }
}
