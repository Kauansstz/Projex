package com.kauan.projex.security;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
     @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        String ip = request.getRemoteAddr();

        InfoUser usuario = usuarioRepository.findByEmail(username);

        if (usuario != null) {
            usuario.setIpUltimoLogin(ip);
            usuario.setUltimoLogin(Timestamp.valueOf(LocalDateTime.now()));
            usuarioRepository.save(usuario);
        }
    }
}
