package com.sparta.hanghaestartproject.handler;

import com.sparta.hanghaestartproject.dto.ErrorResponseDto;
import com.sparta.hanghaestartproject.errorcode.ErrorCode;
import com.sparta.hanghaestartproject.exception.RestApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
     
     @ExceptionHandler (RestApiException.class)
     public ResponseEntity<Object> handleCustomException(RestApiException e) {
          ErrorCode errorCode = e.getErrorCode();
          return handleExceptionInternal(errorCode);
     }
     
     private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
          return ResponseEntity.status(errorCode.getStatusCode())
               .body(makeErrorResponse(errorCode));
     }
     
     private ErrorResponseDto makeErrorResponse(ErrorCode errorCode) {
          return ErrorResponseDto.builder()
               .statusCode(errorCode.getStatusCode())
               .msg(errorCode.getMsg())
               .build();
     }
}
