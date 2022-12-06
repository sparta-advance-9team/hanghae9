package com.sparta.hanghaestartproject.service;

import com.sparta.hanghaestartproject.dto.LoginRequestDto;
import com.sparta.hanghaestartproject.dto.ResponseDto;
import com.sparta.hanghaestartproject.dto.SignupRequestDto;
import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.entity.UserRoleEnum;
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
     
     public ResponseDto signup(SignupRequestDto signupRequestDto) {
          String username = signupRequestDto.getUsername();
          String password = signupRequestDto.getPassword();
     
          // 회원 중복 확인
          Optional<User> found = userRepository.findByUsername(username);
          if (found.isPresent()) {
               return ResponseDto.fail("중복된 username 입니다.");
          }
          UserRoleEnum role = UserRoleEnum.USER;
          if(signupRequestDto.isAdmin()){
               if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                    return ResponseDto.fail("관리자 암호가 틀려 등록이 불가능합니다.");
               }
               role = UserRoleEnum.ADMIN;
          }
          User user = new User(username, password, role);
          userRepository.save(user);
          return ResponseDto.success("회원가입 성공");
     }
     
     public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
          String username = loginRequestDto.getUsername();
          String password = loginRequestDto.getPassword();
     
          // 사용자 확인
          if(!userRepository.existsByUsername(username)){
               return ResponseDto.fail("회원을 찾을 수 없습니다.");
          };
          User user = userRepository.findByUsername(username).get();
          // 비밀번호 확인
          if(!user.getPassword().equals(password)){
               return ResponseDto.fail("비밀번호가 일치하지 않습니다.");
          }
          response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
          return ResponseDto.success("로그인 성공");
     }
}
