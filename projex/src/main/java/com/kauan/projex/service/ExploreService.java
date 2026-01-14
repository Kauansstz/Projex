package com.kauan.projex.service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.kauan.projex.model.InfoUser;
import com.kauan.projex.model.Tecnologia;
import com.kauan.projex.repository.ExploreRepository;
import com.kauan.projex.repository.TecnologiaRepository;

@Service
public class ExploreService {
    private final ExploreRepository explore;
    private final TecnologiaRepository tecnologia;

    public ExploreService(ExploreRepository explore, TecnologiaRepository tecnologia){
        this.explore = explore;
        this.tecnologia = tecnologia;
    }

    public List<InfoUser> buscarPorNome(String nome){
        return explore.findByNameContainingIgnoreCase(nome);
    }
    public List<Tecnologia> buscarPorTecnologia(String nome){
        return tecnologia.findByNomeContainingIgnoreCase(nome);
    }

    public List<InfoUser> listarTodos(){
        return explore.findAll();
    }
    
}
