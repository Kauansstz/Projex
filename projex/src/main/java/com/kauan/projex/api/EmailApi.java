package com.kauan.projex.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/v1/email/")
public class EmailApi {
    private UsuarioRepository repository;
    public EmailApi(UsuarioRepository repository){
        this.repository = repository;
    }

    @GetMapping
    private ResponseEntity<List<InfoUser>> listarEmailUser(@RequestParam(required = false) String email){
        List<InfoUser> lista = repository.findByEmail(email);
        if (email == null || email.isEmpty() ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(lista);
    }
}
