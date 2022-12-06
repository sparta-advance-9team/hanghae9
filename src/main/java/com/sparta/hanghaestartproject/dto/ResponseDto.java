package com.sparta.hanghaestartproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> implements ResonseImpl{
     private String msg;
     private int statusCode;
     
     public static <T>ResponseDto<T> fail(String msg, int statusCode){
          return new ResponseDto<>(msg, statusCode);
     }
     
     public static <T>ResponseDto<T> success(String msg, int statusCode){
          return new ResponseDto<>(msg, statusCode);
     }
}
