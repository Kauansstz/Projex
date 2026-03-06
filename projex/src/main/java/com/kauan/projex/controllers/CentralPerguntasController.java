package com.kauan.projex.controllers;

import com.kauan.projex.model.Pergunta;
import com.kauan.projex.repository.PerguntaRepository;
import com.kauan.projex.utils.Category; 
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CentralPerguntasController {
    
    private final PerguntaRepository perguntaRepository;
    

    public CentralPerguntasController(PerguntaRepository perguntaRepository){
        this.perguntaRepository = perguntaRepository;
    }

    @GetMapping("/centralPerguntas")
    public String abrirCentralPerguntas(Model model) {
        List<Pergunta> perguntas = perguntaRepository.findAll();
        model.addAttribute("perguntas", perguntas);
        model.addAttribute("categorias", Category.values());

        return "pages/panelCenterQuest"; 
    }
}