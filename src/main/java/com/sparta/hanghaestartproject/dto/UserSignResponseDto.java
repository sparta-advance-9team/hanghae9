package com.sparta.hanghaestartproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

//@Getter
//@NoArgsConstructor
public class UserSignResponseDto {
     public UserSignResponseDto(){}
     private String msg = "회원가입 성공";
     private int statusCode = 200;

     public String getMsg() {
          return msg;
     }

     public int getStatusCode() {
          return statusCode;
     }
}
