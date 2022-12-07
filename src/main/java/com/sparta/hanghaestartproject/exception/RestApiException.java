package com.sparta.hanghaestartproject.exception;

import com.sparta.hanghaestartproject.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException{
     private final ErrorCode errorCode;
}
