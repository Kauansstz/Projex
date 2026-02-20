package com.kauan.projex.service;

import com.kauan.projex.model.Categoria;
import com.kauan.projex.repository.CategoriaPerguntasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaPerguntasRepository categoriaRepository;

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }
}

