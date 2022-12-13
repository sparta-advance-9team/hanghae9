package com.sparta.hanghaestartproject.exception;

import com.sparta.hanghaestartproject.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class RestApiException extends RuntimeException{
     
     // 필드값
     private final ErrorCode errorCode;
     
     //getter
     public ErrorCode getErrorCode(){
          return this.errorCode;
     }
     
     // 생성자
     public RestApiException(ErrorCode errorCode){
          this.errorCode = errorCode;
     }
}
