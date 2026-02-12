package com.kauan.projex.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.CenterUserRepository;

@Service
public class CenterUserService {
    private final CenterUserRepository repository;

    public CenterUserService(CenterUserRepository repository){
        this.repository = repository;
    }

    public List<InfoUser> ListarAllUsers(){
        return repository.findAll();
    }

    public List<InfoUser> buscarPorNameUser(String nomeUser){
        return repository.findByNameUserContainingIgnoreCase(nomeUser);
    }

    public List<InfoUser> buscarPorNome(String nome){
        return repository.findByNameContainingIgnoreCase(nome);
    } 
    public InfoUser buscarPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new WorkFlowException("Não foi possivel encontrar o usuário"));
    } 

    public List<InfoUser> buscarPorAtivo(Boolean isAtivo){
        return repository.findByAtivo(isAtivo);
    }

    public void inativarUsuario(Long idUsuario, Long idUsuarioLogado) {
    
    InfoUser usuario = repository.findById(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    usuario.setAtivo(false);
    usuario.setDataInativacao(LocalDateTime.now());
    usuario.setInativadoPor(idUsuarioLogado);

    repository.save(usuario);
}

    public InfoUser salvar(InfoUser info){
        return repository.save(info);
    }
    
}
