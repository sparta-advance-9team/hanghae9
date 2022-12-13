package com.sparta.hanghaestartproject.errorcode;

public class SecurityExceptionCode implements ErrorCode {
     
     
     private final String msg;
     private final int statusCode;
     
     public SecurityExceptionCode(String msg, int statusCode){
          this.msg = msg;
          this.statusCode = statusCode;
     }
     
     @Override
     public String getMsg() {
          return this.msg;
     }
     
     @Override
     public int getStatusCode() {
          return this.statusCode;
     }
}
