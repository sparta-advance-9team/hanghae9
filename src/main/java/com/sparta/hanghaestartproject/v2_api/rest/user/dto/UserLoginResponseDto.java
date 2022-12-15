package com.sparta.hanghaestartproject.v2_api.rest.user.dto;

//@Getter
//@NoArgsConstructor
public class UserLoginResponseDto {
     public UserLoginResponseDto(){}
     private String msg = "로그인 성공";

     private int statusCode = 200;

     public String getMsg() {
          return msg;
     }

     public int getStatusCode() {
          return statusCode;
     }
}
