package com.kauan.projex.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kauan.projex.repository.UsuarioRepository;
import com.kauan.projex.exceptions.WorkFlowException;


import com.kauan.projex.model.InfoUser;

@Service
public class UserService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public InfoUser authenticator(String email, String password){
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos!");
        }
        InfoUser foundUser = usuarioRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new WorkFlowException("Usuário ou senha incorretos."));
        System.out.println("🔎 Resultado da busca no banco: " + foundUser);
        
        String token = UUID.randomUUID().toString();
        foundUser.setToken(token);
        usuarioRepository.save(foundUser);

        System.out.println("Usuário autenticado: " + foundUser.getName() + " | " + foundUser.getEmail());
        System.out.println("Token gerado: " + token);

        return foundUser;
    }
    public InfoUser validarToken(String token) {
        InfoUser found = usuarioRepository.findByToken(token).orElseThrow(() -> new WorkFlowException("Acesso negado: token inválido ou expirado."));
        return found;
    }
}
