package com.smartload.optimizer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public org.springframework.http.ResponseEntity<String> handleMaxSizeException() {
        return org.springframework.http.ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("Payload too large");
    }
}
