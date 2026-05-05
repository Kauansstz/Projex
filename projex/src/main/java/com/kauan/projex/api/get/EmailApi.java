package com.kauan.projex.api.get;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/email")
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

    @PostMapping("/login")
    public ResponseEntity<?> autenticar(@RequestBody Map<String, String> dados, HttpServletRequest request){
        String email = dados.get("email");
        String password = dados.get("password");
        List<InfoUser> user = repository.findByEmailAndPassword(email, password);
        if (user == null || user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
        InfoUser usuario = user.get(0);

        if (!usuario.getAtivo()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Conta desativada.");
        }

        request.getSession().setAttribute("usuarioLogado", usuario);
        return ResponseEntity.ok(usuario); 
    }
}
