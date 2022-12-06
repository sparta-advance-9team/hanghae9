package com.sparta.hanghaestartproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> implements ResponseImpl {
     private String msg;
     private int statusCode;
     
     public static <T>ResponseDto<T> fail(String msg, int statusCode){
          return new ResponseDto<>(msg, statusCode);
     }
     
     public static <T>ResponseDto<T> success(String msg, int statusCode){
          return new ResponseDto<>(msg, statusCode);
     }
}
