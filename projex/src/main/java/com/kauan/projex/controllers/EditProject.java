package com.kauan.projex.controllers;

import org.springframework.ui.Model;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.repository.TecnologiaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class EditProject {
    private final TecnologiaRepository tecnologiaRepository;

    public EditProject(TecnologiaRepository tecnologiaRepository) {
        this.tecnologiaRepository = tecnologiaRepository;
    }
    @GetMapping("/editProject")
    public String dashboardEdit(Model model){
        model.addAttribute("projeto", new InfoProject());
        model.addAttribute("tecnologias", tecnologiaRepository.findAll());
        model.addAttribute("pageTitle", "Editar Projeto");
        return "pages/panelEditProject";
    }
}
