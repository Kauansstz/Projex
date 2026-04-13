package com.kauan.projex.api.post;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.UsuarioRepository;

@RestController
@RequestMapping("api/v1/create")
public class CreateUsuario {
    private final UsuarioRepository repository;

    public CreateUsuario(UsuarioRepository repository){
        this.repository = repository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> criarUsuario(@RequestBody Map<String, Object> dados){
        if (!dados.containsKey("email") || dados.get("email").toString().isEmpty()) {
            return ResponseEntity.badRequest().body("Email obrigatório para o cadastro");
        }

        if (!dados.containsKey("password")) {
            return ResponseEntity.badRequest().body("Senha é obrigatória");
        }

        try{
            InfoUser novoUsuario = new InfoUser();

            novoUsuario.setName(dados.getOrDefault("name", "Default").toString());
            novoUsuario.setEmail(dados.getOrDefault("email", "email@email.com").toString());
            novoUsuario.setPassword(dados.getOrDefault("password", "12345").toString());
            novoUsuario.setCpf(dados.getOrDefault("cpf", "1234567891011").toString());
            novoUsuario.setRole(dados.getOrDefault("role", "ROLE_DEFAULT").toString());
            novoUsuario.setAceitarTermos(true);
            novoUsuario.setConfirmPassword(dados.getOrDefault("confirmPassword", "").toString());
            novoUsuario.setTentativasLogin(0);
            novoUsuario.setIpCriacao("127.0.0.1");
            novoUsuario.setToken("token-inicial");
            if (dados.containsKey("dataNasc")) {
                String dataString = dados.getOrDefault("dataNasc", "2026-01-01").toString();
                LocalDate dataConvertida = LocalDate.parse(dataString);
                novoUsuario.setDataNasc(dataConvertida);
            }

            InfoUser salvo = repository.save(novoUsuario);
            return ResponseEntity.ok(salvo);
    } catch(WorkFlowException e){
        return ResponseEntity.internalServerError().body("Erro ao criar usuário: " + e.getMessage());
    }
    }
}
