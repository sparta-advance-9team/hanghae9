package com.sparta.hanghaestartproject.handler;

import com.sparta.hanghaestartproject.dto.ErrorResponseDto;
import com.sparta.hanghaestartproject.errorcode.CommonErrorCode;
import com.sparta.hanghaestartproject.errorcode.ErrorCode;
import com.sparta.hanghaestartproject.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
     
     @ExceptionHandler (RestApiException.class)
     public ResponseEntity<Object> handleCustomException(RestApiException e) {
          ErrorCode errorCode = e.getErrorCode();
          return handleExceptionInternal(errorCode);
     }
     
     @ExceptionHandler(IllegalArgumentException.class)
     public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
          log.warn("handleIllegalArgument", e);
          ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
          return handleExceptionInternal(errorCode, e.getMessage());
     }
     
     @ExceptionHandler({Exception.class})
     public ResponseEntity<Object> handleAllException(Exception ex) {
          log.warn("handleAllException", ex);
          ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
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
     
     private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
          return ResponseEntity.status(errorCode.getStatusCode())
               .body(makeErrorResponse(errorCode, message));
     }
     
     private ErrorResponseDto makeErrorResponse(ErrorCode errorCode, String message) {
          return ErrorResponseDto.builder()
               .statusCode(errorCode.getStatusCode())
               .msg(message)
               .build();
     }
}
