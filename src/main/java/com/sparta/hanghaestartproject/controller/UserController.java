package com.sparta.hanghaestartproject.controller;

import com.sparta.hanghaestartproject.dto.LoginRequestDto;
import com.sparta.hanghaestartproject.dto.SignupRequestDto;
import com.sparta.hanghaestartproject.dto.UserLoginResponseDto;
import com.sparta.hanghaestartproject.dto.UserSignResponseDto;
import com.sparta.hanghaestartproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping ("/api/user")
@RequiredArgsConstructor
public class UserController {
     
     private final UserService userService;
     
     @PostMapping ("/signup")
     public UserSignResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
          userService.signup(signupRequestDto);
          return new UserSignResponseDto();
     }
     
     @ResponseBody
     @PostMapping("/login")
     public UserLoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
          userService.login(loginRequestDto, response);
          return new UserLoginResponseDto();
     }
}
