package com.sparta.hanghaestartproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> implements ResponseImpl {
     private String msg;
     private int statusCode;
     
     public static <T>ResponseDto<T> fail(String msg){
          return new ResponseDto<>(msg, HttpStatus.BAD_REQUEST.value());
     }
     
     public static <T>ResponseDto<T> success(String msg){
          return new ResponseDto<>(msg, HttpStatus.OK.value());
     }
}
