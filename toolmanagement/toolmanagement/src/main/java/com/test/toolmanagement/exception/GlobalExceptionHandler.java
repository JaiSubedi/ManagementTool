package com.test.toolmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ToolNotFoundException.class)
    public ResponseEntity<ErrorDto> handleToolNotFoundException(ToolNotFoundException ex) {
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .message(ex.getMessage())
                        .errorCode(400)
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDto> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .message(ex.getMessage())
                        .errorCode(400)
                        .build(),
                HttpStatus.BAD_REQUEST);
    }
}
