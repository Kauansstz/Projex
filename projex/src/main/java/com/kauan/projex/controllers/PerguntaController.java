package com.kauan.projex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.kauan.projex.model.Pergunta;
import com.kauan.projex.repository.PerguntaRepository;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/perguntas")
public class PerguntaController {

    private final PerguntaRepository perguntaRepository;

    public PerguntaController(PerguntaRepository perguntaRepository) {
        this.perguntaRepository = perguntaRepository;
    }

    @GetMapping
    public String listarPerguntas(Model model) {

        List<Pergunta> perguntas = perguntaRepository.findAll();

        model.addAttribute("perguntas", perguntas);

        return "pages/panelQuest";
    }
}