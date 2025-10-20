package com.kauan.projex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.kauan.projex.repository.TecnologiaRepository;

import com.kauan.projex.model.InforProject;

@Controller
public class PageController {
    private final TecnologiaRepository tecnologiaRepository;

    public PageController(TecnologiaRepository tecnologiaRepository) {
        this.tecnologiaRepository = tecnologiaRepository;
    }

    @GetMapping("/project")
    public String project(Model model){
        model.addAttribute("projeto", new InforProject());
        model.addAttribute("tecnologias", tecnologiaRepository.findAll());
        model.addAttribute("pageTitle", "Cadastrar Projeto");
        return "pages/newProject";
    }
    @GetMapping("/panelProjetos")
    public String panelProject(Model model){
        model.addAttribute("pageTitle", "Projetos");
        return "pages/projectes";
    }
    @GetMapping("/panelAccount")
    public String panelAccount(Model model){
        model.addAttribute("pageTitle", "Perfil");
        return "pages/account";
    }
    @GetMapping("/dashboardEdit")
    public String dashboardEdit(Model model){
        model.addAttribute("projeto", new InforProject());
        model.addAttribute("tecnologias", tecnologiaRepository.findAll());
        model.addAttribute("pageTitle", "Editar Projeto");
        return "pages/dashboardEdit";
    }
    @GetMapping("/explore")
    public String explore(Model model){
        model.addAttribute("pageTitle", "Explorar");
        return "pages/explorer";
    }
    @GetMapping("/")
    public String dashboard(Model model){
        model.addAttribute("pageTitle", "Dashboard");
        return "pages/dashboard";
    }
}
