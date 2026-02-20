package com.kauan.projex.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.kauan.projex.model.Categoria;
import com.kauan.projex.repository.CategoriaPerguntasRepository;


@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaPerguntasRepository categoriaRepository;

    public CategoriaController(CategoriaPerguntasRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<Categoria> listarCategoriasAtivas() {
        return categoriaRepository.findByAtivoTrue();
    }
}
