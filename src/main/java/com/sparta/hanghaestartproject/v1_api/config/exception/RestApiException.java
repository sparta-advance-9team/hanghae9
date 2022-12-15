package com.sparta.hanghaestartproject.v1_api.config.exception;

import com.sparta.hanghaestartproject.config.errorcode.ErrorCode;

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
