package com.kauan.projex.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHanhler {
    
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Map<String, Object>> handlerDuplicate(DuplicateException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
            "status",409,
            "error", "Conflito de dados",
            "message", ex.getMessage(),
            "timestamp", LocalDateTime.now().toString()
        ));
    }
}
