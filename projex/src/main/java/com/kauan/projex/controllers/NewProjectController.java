package com.kauan.projex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.kauan.projex.repository.TecnologiaRepository;
import com.kauan.projex.model.InforProject;


@Controller
public class NewProjectController {
    private final TecnologiaRepository tecnologiaRepository;

    public NewProjectController(TecnologiaRepository tecnologiaRepository) {
        this.tecnologiaRepository = tecnologiaRepository;
    }
    @GetMapping("/project")
    public String project(Model model){
        model.addAttribute("projeto", new InforProject());
        model.addAttribute("tecnologias", tecnologiaRepository.findAll());
        model.addAttribute("pageTitle", "Cadastrar Projeto");
        return "pages/newProject";
    }
}
