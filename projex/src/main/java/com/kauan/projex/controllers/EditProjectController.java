package com.kauan.projex.controllers;

import com.kauan.projex.model.InfoProject;
import com.kauan.projex.repository.TecnologiaRepository;
import com.kauan.projex.service.EditCardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panelProjetos")
public class EditProjectController {

    private final EditCardService cardService;
    private final TecnologiaRepository tecnologiaRepository;

    public EditProjectController(
            EditCardService cardService,
            TecnologiaRepository tecnologiaRepository) {
        this.cardService = cardService;
        this.tecnologiaRepository = tecnologiaRepository;
    }

    @PostMapping("/{id}/editar")
    public String editProject(){
        return "redirect:/panelProjetos";
    }

    @GetMapping("/{id}/editar")
    public String editarProjeto(@PathVariable Long id, Model model) {
        InfoProject projeto = cardService.buscarPorId(id);

        model.addAttribute("projeto", projeto);
        model.addAttribute("tecnologias", tecnologiaRepository.findAll());
        model.addAttribute("pageTitle", "Editar Projeto");
        return "pages/panelEditProject";
    }
    @GetMapping("/{id}/viewer")
    public String editarViewer(@PathVariable Long id, Model model) {
        InfoProject projeto = cardService.buscarPorId(id);

        model.addAttribute("projeto", projeto);
        model.addAttribute("tecnologias", tecnologiaRepository.findAll());
        model.addAttribute("pageTitle", "Visualizar Projeto");

        return "pages/panelViewerProject";
    }
}
