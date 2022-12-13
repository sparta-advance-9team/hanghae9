package com.sparta.hanghaestartproject.controller;

import com.sparta.hanghaestartproject.dto.*;
import com.sparta.hanghaestartproject.entity.User;
import com.sparta.hanghaestartproject.entity.UserRoleEnum;
import com.sparta.hanghaestartproject.errorcode.ErrorCode;
import com.sparta.hanghaestartproject.errorcode.UserErrorCode;
import com.sparta.hanghaestartproject.exception.RestApiException;
import com.sparta.hanghaestartproject.repository.UserRepository;
import com.sparta.hanghaestartproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping ("/api/user")
//@RequiredArgsConstructor의 역할.. final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동으로 생성해준다. 17~20줄 참조
public class UserController {
     private final UserService userService;
     private final PasswordEncoder passwordEncoder;
     private final UserRepository userRepository;
     
     private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
     
     //AllRequiredArgs Constructor
     public UserController(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
         this.userService = userService;
         this.passwordEncoder = passwordEncoder;
         this.userRepository = userRepository;
     }
     
     @PostMapping ("/signup")
     public CompleteResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
          return userService.signup(signupRequestDto);
     }
     
//     @PostMapping("/login")
//     public String login(@AuthenticationPrincipal UserDetails userDetails)
     @ResponseBody
     @PostMapping("/login")
     public CompleteResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
          return userService.login(loginRequestDto, response);
     }
     
     @GetMapping("/login-page")
     public ModelAndView loginPage() {
          return new ModelAndView("login");
     }
     
     //@Secured(value = UserRoleEnum.Authority.ADMIN) 관리자용
     
     @PostMapping("/forbidden")
     public ModelAndView forbidden() {
          return new ModelAndView("forbidden");
     }
}
