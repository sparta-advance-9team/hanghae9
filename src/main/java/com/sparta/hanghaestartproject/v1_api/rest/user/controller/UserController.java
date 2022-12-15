package com.sparta.hanghaestartproject.v1_api.rest.user.controller;

import com.sparta.hanghaestartproject.config.dto.CompleteResponseDto;
import com.sparta.hanghaestartproject.v1_api.rest.user.dto.LoginRequestDto;
import com.sparta.hanghaestartproject.v1_api.rest.user.dto.SignupRequestDto;
import com.sparta.hanghaestartproject.v1_api.rest.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping ("/api/user")
//@RequiredArgsConstructor의 역할.. final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동으로 생성해준다. 17~20줄 참조
public class UserController {
     private final UserService userService;
     
     //AllRequiredArgs Constructor
     public UserController(UserService userService) {
         this.userService = userService;
     }
     
     @PostMapping ("/signup")
     public CompleteResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {

          return userService.signup(signupRequestDto);
     }

     @PostMapping("/login")
     public CompleteResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
          return userService.login(loginRequestDto, response);
     }

     //@Secured(value = UserRoleEnum.Authority.ADMIN) 관리자용
}
