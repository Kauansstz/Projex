package com.kauan.projex.controllers;

import org.springframework.ui.Model;
import com.kauan.projex.model.InforProject;
import com.kauan.projex.repository.TecnologiaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class EditDashboardController {
    private final TecnologiaRepository tecnologiaRepository;

    public EditDashboardController(TecnologiaRepository tecnologiaRepository) {
        this.tecnologiaRepository = tecnologiaRepository;
    }
    @GetMapping("/dashboardEdit")
    public String dashboardEdit(Model model){
        model.addAttribute("projeto", new InforProject());
        model.addAttribute("tecnologias", tecnologiaRepository.findAll());
        model.addAttribute("pageTitle", "Editar Projeto");
        return "pages/dashboardEdit";
    }
}
