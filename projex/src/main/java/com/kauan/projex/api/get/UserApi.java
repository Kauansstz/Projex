package com.kauan.projex.api.get;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/v1/user")
public class UserApi {
    private UsuarioRepository repository;

    public UserApi(UsuarioRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<InfoUser>> listarUser(@RequestParam(required = false) String cpf){
        if (cpf == null || cpf.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<InfoUser> usuario = repository.findByCpf(cpf);
     
        return ResponseEntity.ok(usuario);
    }
    @GetMapping("/all")
    public ResponseEntity<List<InfoUser>> listarTodos(){
        List<InfoUser> usuario = repository.findAll();
        
        if (usuario == null || usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(usuario);
    }
}
