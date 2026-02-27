package com.kauan.projex.controllers;

import org.springframework.web.bind.annotation.*;
import com.kauan.projex.model.Pergunta;
import com.kauan.projex.repository.PerguntaRepository;
import com.kauan.projex.utils.Category;

import java.util.List;

@RestController
@RequestMapping("/api/perguntas")
public class PerguntaRestController {

    private final PerguntaRepository perguntaRepository;

    public PerguntaRestController(PerguntaRepository perguntaRepository) {
        this.perguntaRepository = perguntaRepository;
    }

    @GetMapping("/categoria/{categoria}")
    public List<Pergunta> buscarPorCategoria(@PathVariable Category categoria) {
      
        return perguntaRepository.findByCategoria(categoria);
    }
}