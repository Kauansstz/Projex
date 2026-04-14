package com.kauan.projex.api.put;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kauan.projex.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/user/inativar")
public class InativarUsuarioApi {
    private final UsuarioRepository repository;

    public InativarUsuarioApi(UsuarioRepository repository){
        this.repository = repository;
    }

    @PutMapping("/{id}/desabilitar")
    public ResponseEntity<?> inativarUsuario(@PathVariable Long id){
        return repository.findById(id).map(
            usuario -> {usuario.setAtivo(false);
            usuario.setDataInativacao(java.time.LocalDateTime.now());
            repository.save(usuario);
            return ResponseEntity.ok("Usuário desativado: " + usuario);
        }
        ).orElse(ResponseEntity.notFound().build());
    }
}
