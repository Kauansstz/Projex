package com.kauan.projex.controllers;
import java.util.Collections;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.kauan.projex.model.Pergunta;
import com.kauan.projex.model.Resposta;
import com.kauan.projex.repository.PerguntaRepository;
import com.kauan.projex.utils.Category;

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
    public String listarPerguntas(@RequestParam(value = "categoria", required = false) String categoriaStr,
            @RequestParam(value = "categoria", required = false) String categoria,
            @RequestParam(value = "nivel", required = false) String nivel,
            @RequestParam(value = "resposta", required = false) Resposta resposta,
            Model model) {

        if (categoriaStr != null) {
        try {
            Category categoriaEnum = com.kauan.projex.utils.Category.valueOf(categoriaStr);
            model.addAttribute("categoriaSelecionada", categoriaEnum);
            if (nivel != null) {
                List<Pergunta> perguntas = perguntaRepository.findByCategoriaAndNivel(categoriaEnum, nivel);
                List<Pergunta> respostas = perguntaRepository.findByCategoriaAndNivelAndRespostas(categoriaEnum, nivel, resposta);
                Collections.shuffle(respostas);

               for (Pergunta p : perguntas) {
                    List<Resposta> r = p.getRespostas();
                    if (r != null && r.size() >= 4) {
                        Collections.shuffle(r);
                    }
                }
                model.addAttribute("perguntas", perguntas);
                model.addAttribute("nivelSelecionado", nivel);
            }
        } catch (IllegalArgumentException e) {
            return "redirect:/perguntas"; 
        }
    }

        return "pages/panelCenterQuest";
    }

}