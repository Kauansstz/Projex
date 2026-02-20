package com.kauan.projex.controllers;


import com.kauan.projex.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CentralPerguntasController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/centralPerguntas")
    public String abrirCentralPerguntas(Model model) {

        model.addAttribute("categorias", categoriaService.listarTodas());

        return "pages/panelQuest"; // nome do seu html
    }
}
