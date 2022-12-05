package com.sparta.hanghaestartproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginResponseDto {
     private String msg = "로그인 성공";
     private int statusCode = 200;
}
