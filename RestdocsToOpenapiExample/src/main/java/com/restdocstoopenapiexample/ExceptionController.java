package com.restdocstoopenapiexample;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> illegalStateException(IllegalStateException e) {
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.builder()
                        .error(e.getMessage())
                        .responseCode("ErrorCode")
                        .message("message")
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .error(e.getFieldErrors().stream().map(FieldError::getField).sorted().reduce("", String::concat))
                        .responseCode("ErrorCode")
                        .message("message")
                        .build());
    }
}
