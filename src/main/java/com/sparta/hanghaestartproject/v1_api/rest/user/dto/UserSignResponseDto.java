package com.sparta.hanghaestartproject.v1_api.rest.user.dto;

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
