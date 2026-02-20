package com.kauan.projex.controllers;

import org.springframework.web.bind.annotation.*;

import com.kauan.projex.model.Pergunta;
import com.kauan.projex.service.PerguntaService;
import com.kauan.projex.utils.Category;

import java.util.List;

@RestController
@RequestMapping("/perguntas")
public class PerguntaController {

    private final PerguntaService perguntaService;

    public PerguntaController(PerguntaService perguntaService) {
        this.perguntaService = perguntaService;
    }

    @GetMapping("/categoria/{categoria}")
    public List<Pergunta> listarPorCategoria(@PathVariable Category categoria) {
        return perguntaService.listarPorCategoria(categoria);
    }

}

