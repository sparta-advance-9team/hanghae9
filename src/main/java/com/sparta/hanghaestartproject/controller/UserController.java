package com.sparta.hanghaestartproject.controller;

import com.sparta.hanghaestartproject.dto.*;
import com.sparta.hanghaestartproject.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping ("/api/user")
//@RequiredArgsConstructor의 역할.. final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동으로 생성해준다. 17~20줄 참조
//.
public class UserController {
     
     private final UserService userService;

     public UserController(UserService userService) {
          this.userService = userService;
     }

     @PostMapping ("/signup")
     public CompleteResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
          return userService.signup(signupRequestDto);
     }
     
     @ResponseBody
     @PostMapping("/login")
     public CompleteResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
          return userService.login(loginRequestDto, response);
     }
}
