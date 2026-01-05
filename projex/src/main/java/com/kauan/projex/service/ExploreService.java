package com.kauan.projex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.ExploreRepository;

@Service
public class ExploreService {
    private final ExploreRepository explore;

    public ExploreService(ExploreRepository explore){
        this.explore = explore;
    }

    public List<InfoUser> buscarPorNome(String nome){
        return explore.findByNameContainingIgnoreCase(nome);
    }

    public List<InfoUser> listarTodos(){
        return explore.findAll();
    }
    
}
