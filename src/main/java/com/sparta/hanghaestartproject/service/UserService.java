package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.LoginRequestDto;
import com.sparta.hanghaestartproject.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.dto.SignupRequestDto;
import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.entity.UserRoleEnum;
import com.sparta.hanghaestartproject.errorcode.UserErrorCode;
import com.sparta.hanghaestartproject.exception.RestApiException;
import com.sparta.hanghaestartproject.jwt.JwtUtil;
import com.sparta.hanghaestartproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
     static String msg;
     static int statusCode = 400;
     
     private final UserRepository userRepository;
     private final JwtUtil jwtUtil;
     private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
     
     public CompleteResponseDto signup(SignupRequestDto signupRequestDto) {
          String username = signupRequestDto.getUsername();
          String password = signupRequestDto.getPassword();
     
          // 회원 중복 확인
          Optional<User> found = userRepository.findByUsername(username);
          if (found.isPresent()) {
               throw new RestApiException(UserErrorCode.OVERLAPPED_USERNAME);
          }
          UserRoleEnum role = UserRoleEnum.USER;
          if(signupRequestDto.isAdmin()){
               if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                    throw new RestApiException(UserErrorCode.WRONG_ADMINTOKEN);
               }
               role = UserRoleEnum.ADMIN;
          }
          User user = new User(username, password, role);
          userRepository.save(user);
          return CompleteResponseDto.success("회원가입 성공");
     }
     
     public CompleteResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
          String username = loginRequestDto.getUsername();
          String password = loginRequestDto.getPassword();
     
          // 사용자 확인
          if(!userRepository.existsByUsername(username)){
               throw new RestApiException(UserErrorCode.NO_USER);
          };
          User user = userRepository.findByUsername(username).get();
          // 비밀번호 확인
          if(!user.getPassword().equals(password)){
               throw new RestApiException(UserErrorCode.WRONG_PASSWORD);
          }
          response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername())); // getRole();
          return CompleteResponseDto.success("로그인 성공");
     }
}
