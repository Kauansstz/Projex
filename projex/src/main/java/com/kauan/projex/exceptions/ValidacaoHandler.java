package com.kauan.projex.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidacaoHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> tratarErroValidacao(BindException ex) {
        // Isso aqui pega as mensagens amigáveis que você definiu (ex: "O título é obrigatório")
        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        // Retorna apenas o texto e o status 400
        return ResponseEntity.badRequest().body(mensagem);
    }
}