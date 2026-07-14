package com.kauan.projex.api.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kauan.projex.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/v1/delete")
public class DeleteUser {
    
    @Autowired
    private UsuarioRepository repository;

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable("id") @NonNull Long id){
        if(!repository.existsById(id)){
            return ResponseEntity.status(404).body("Usuário não encontrado com o ID: " + id);
        }
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
