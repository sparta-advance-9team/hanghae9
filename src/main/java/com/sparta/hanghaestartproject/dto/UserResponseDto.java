package com.sparta.hanghaestartproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
     private String msg = "회원가입 성공";
     private int statusCode = 200;
}
