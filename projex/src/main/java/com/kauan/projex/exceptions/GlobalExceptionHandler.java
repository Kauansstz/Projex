package com.kauan.projex.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Map<String, Object>> handlerDuplicate(DuplicateException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
            "status",409,
            "error", "Conflito de dados",
            "message", ex.getMessage(),
            "timestamp", LocalDateTime.now().toString()
        ));
    }
    @ExceptionHandler(WorkFlowException.class)
    public String handleWorkflow(
            WorkFlowException ex,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(
            "mensagemErro",
            ex.getMessage()
        );
        return "redirect:/login";
    }
}
