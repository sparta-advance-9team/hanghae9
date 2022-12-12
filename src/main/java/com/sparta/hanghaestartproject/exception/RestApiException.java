package com.sparta.hanghaestartproject.exception;

import com.sparta.hanghaestartproject.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class RestApiException extends RuntimeException{
     private final ErrorCode errorCode;
     
     public ErrorCode getErrorCode(){
          return this.errorCode;
     }
     
     public RestApiException(ErrorCode errorCode){
          this.errorCode = errorCode;
     }
}
