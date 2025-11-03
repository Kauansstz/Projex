package com.kauan.projex.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.UsuarioRepository;

@Component
public class TokenAcesso {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void gerarTokenParaUsuario(InfoUser usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }

        // Gera um token aleatório
        String novoToken = UUID.randomUUID().toString();
        usuario.setToken(novoToken);

        // Define a expiração do token (4 horas a partir de agora)
        String expiracao = LocalDateTime.now()
                .plusHours(4)
                .format(DateTimeFormatter.ISO_DATE_TIME);
        usuario.setResetTokenExpiracao(expiracao);

        // Salva o usuário atualizado no banco
        usuarioRepository.save(usuario);
    }
}
