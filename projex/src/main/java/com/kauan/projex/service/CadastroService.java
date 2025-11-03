package com.kauan.projex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.UsuarioRepository;

@Service
public class CadastroService {
    @Autowired
    private UsuarioRepository repository;

    public InfoUser salvarUsuario(InfoUser usuario){
        if(usuario.getName() == null || usuario.getEmail() == null){
            throw new IllegalArgumentException("Nome e E-mail são obrigatórios.");
        }
        return repository.save(usuario);
    }
}
