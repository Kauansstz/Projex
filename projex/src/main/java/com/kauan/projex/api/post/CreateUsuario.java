package com.kauan.projex.api.post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.UsuarioRepository;
import com.kauan.projex.utils.Genero;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/create")
public class CreateUsuario {
    private final UsuarioRepository repository;

    public CreateUsuario(UsuarioRepository repository){
        this.repository = repository;
    }

    @PostMapping("/usuario")
    public ResponseEntity<?> criarUsuario(@RequestBody Map<String, Object> dados, HttpServletRequest request){
        if (!dados.containsKey("email") || dados.get("email").toString().isEmpty()) {
            return ResponseEntity.badRequest().body("Email obrigatório para o cadastro");
        }

        if (!dados.containsKey("password")) {
            return ResponseEntity.badRequest().body("Senha é obrigatória");
        }

        try{

            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isBlank()) {
                ip = request.getRemoteAddr();
            }
            InfoUser novoUsuario = new InfoUser();
            String generoStr =  dados.getOrDefault("genero", "OUTRO").toString();

            novoUsuario.setName(dados.getOrDefault("name", "Default").toString());
            novoUsuario.setEmail(dados.getOrDefault("email", "email@email.com").toString());
            novoUsuario.setPassword(dados.getOrDefault("password", "12345").toString());
            novoUsuario.setCpf(dados.getOrDefault("cpf", "1234567891011").toString());
            novoUsuario.setRole(dados.getOrDefault("role", "ROLE_DEFAULT").toString());
            novoUsuario.setGenero(Genero.valueOf(generoStr.toUpperCase()));
            novoUsuario.setAceitarTermos(true);
            novoUsuario.setAtivo(true);
            novoUsuario.setAtualizadoEm(LocalDateTime.now());
            novoUsuario.setForcarTrocaSenha(false);
            novoUsuario.setTentativasLogin(0);
            novoUsuario.setUltimoLogin(LocalDateTime.now());
            novoUsuario.setDescricao("Descrição padrão");
            novoUsuario.setFotoPerfil("default.png");
            novoUsuario.setProjetos(new ArrayList<>());
            novoUsuario.setToken(UUID.randomUUID().toString());
            novoUsuario.setConfirmPassword(dados.getOrDefault("confirmPassword", "").toString());
            novoUsuario.setIpCriacao(ip);
            novoUsuario.setIpUltimoLogin(ip);
            novoUsuario.setResetTokenExpiracao(LocalDateTime.now().plusHours(4).format(DateTimeFormatter.ISO_DATE_TIME));
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
