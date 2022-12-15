package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.LoginRequestDto;
import com.sparta.hanghaestartproject.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.dto.SignupRequestDto;
import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.entity.UserRoleEnum;
import com.sparta.hanghaestartproject.errorcode.UserErrorCode;
import com.sparta.hanghaestartproject.exception.RestApiException;
import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.CommentRepository;
import com.sparta.hanghaestartproject.repository.PostRepository;
import com.sparta.hanghaestartproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Service
//@RequiredArgsConstructor의 역할.. final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동으로 생성해준다. 30~33줄 참고
public class UserService {
     
     static String msg;
     static int statusCode = 400;
     
     private final UserRepository userRepository;
     private final PostRepository postRepository;
     private final CommentRepository commentRepository;
     private final PasswordEncoder passwordEncoder;
     private final JwtUtil jwtUtil;
     @Value ("${admin.token}")
     private String ADMIN_TOKEN;
     
     public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil
     , PostRepository postRepository, CommentRepository commentRepository) {
          this.userRepository = userRepository;
          this.passwordEncoder = passwordEncoder;
          this.jwtUtil = jwtUtil;
          this.postRepository = postRepository;
          this.commentRepository = commentRepository;
     }
     
     @Transactional
     public CompleteResponseDto signup(SignupRequestDto signupRequestDto) {
          String username = signupRequestDto.getUsername();
          String password = passwordEncoder.encode(signupRequestDto.getPassword());
          
          // 회원 중복 확인
          Optional<User> found = userRepository.findByUsername(username);
          if (found.isPresent()) {
               throw new RestApiException(UserErrorCode.OVERLAPPED_USERNAME);
          }
          UserRoleEnum role = UserRoleEnum.USER;
          if (signupRequestDto.isAdmin()) {
               if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                    throw new RestApiException(UserErrorCode.WRONG_ADMINTOKEN);
               }
               role = UserRoleEnum.ADMIN;
          }
          User user = new User(username, password, role);
          userRepository.save(user);
          return CompleteResponseDto.success("회원가입 성공");
     }
     
     @Transactional (readOnly = true)
     public CompleteResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
          String username = loginRequestDto.getUsername();
          String password = loginRequestDto.getPassword();
          
          // 사용자 확인
          User user = userRepository.findByUsername(username).orElseThrow(
               () -> new RestApiException(UserErrorCode.NO_USER)
          );
          // 비밀번호 확인
          if (!passwordEncoder.matches(password, user.getPassword())) {
               throw new RestApiException(UserErrorCode.WRONG_PASSWORD);
          }
          response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole())); // getRole();
          return CompleteResponseDto.success("로그인 성공");
     }
     
     @Transactional
     public CompleteResponseDto deleteUserByAdmin(LoginRequestDto loginRequestDto, User admin) {
          if (admin.getRole().equals(UserRoleEnum.USER)) {
               throw new RestApiException(UserErrorCode.ONLY_FOR_ADMIN);
          }
          User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
               () -> new RestApiException(UserErrorCode.NO_USER)
          );
          // 1. username 가진  post 삭제
          // 2. username 가진 comment 삭제
          postRepository.deleteAllByUsername(user.getUsername());
          commentRepository.deleteAllByUsername(user.getUsername());
          userRepository.deleteByUsername(user.getUsername());
          return CompleteResponseDto.success("유저삭제 성공");
     }
     
     public CompleteResponseDto quitUser(LoginRequestDto loginRequestDto, User user) {
          return CompleteResponseDto.success("유저탈퇴 성공");
     }
}
