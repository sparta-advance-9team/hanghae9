package com.sparta.hanghaestartproject.v2_api.rest.user.service;

import com.sparta.hanghaestartproject.v1_api.rest.user.dto.LoginRequestDto;
import com.sparta.hanghaestartproject.config.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.v1_api.rest.user.dto.SignupRequestDto;
import com.sparta.hanghaestartproject.v1_api.rest.user.model.User;
import com.sparta.hanghaestartproject.config.model.UserRoleEnum;
import com.sparta.hanghaestartproject.config.errorcode.UserErrorCode;
import com.sparta.hanghaestartproject.config.exception.RestApiException;
import com.sparta.hanghaestartproject.config.jwt.JwtUtil;
import com.sparta.hanghaestartproject.v1_api.rest.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
//@RequiredArgsConstructor의 역할.. final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동으로 생성해준다. 30~33줄 참고
public class UserService {

     static String msg;
     static int statusCode = 400;
     
     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;
     private final JwtUtil jwtUtil;

     private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

     public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
          this.userRepository = userRepository;
          this.passwordEncoder = passwordEncoder;
          this.jwtUtil = jwtUtil;
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

          // 불필요한 객체가 생성이된다거나
          // 불필요한 if문이 있다거나
          // 가독성이 떨어지는 로직이 있다거나
          // 중복코드 -> 메서드로 바꿔서 코드의 재사용성을 높히거나
          UserRoleEnum role;

          if(signupRequestDto.isAdmin()){
               if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                    throw new RestApiException(UserErrorCode.WRONG_ADMINTOKEN);
               }
               role = UserRoleEnum.ADMIN;
          }else {
               role = UserRoleEnum.USER;
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
          if(!passwordEncoder.matches(password, user.getPassword())){
               throw new RestApiException(UserErrorCode.WRONG_PASSWORD);
          }

          response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole())); // getRole();

          return CompleteResponseDto.success("로그인 성공");
     }
}
