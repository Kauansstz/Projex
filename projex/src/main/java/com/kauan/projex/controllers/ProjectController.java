package com.kauan.projex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kauan.projex.model.InfoProject;
import com.kauan.projex.service.CardService;


@Controller
@RequestMapping("/panelProjetos")
public class ProjectController {

    private final CardService cardService;

    public ProjectController(CardService cardService) {
        this.cardService = cardService;
    }

    // LISTAR + BUSCAR
    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String search) {

        if (search != null && !search.isBlank()) {
            model.addAttribute("cards", cardService.buscarPorTitulo(search));
        } else {
            model.addAttribute("cards", cardService.listarTodos());
        }

        model.addAttribute("search", search);
        model.addAttribute("pageTitle", "Projetos");
        return "pages/projectes";
    }

    // VISUALIZAR
    @GetMapping("/{id}")
    public String visualizar(@PathVariable Long id, Model model) {
        InfoProject projeto = cardService.buscarPorId(id);
        model.addAttribute("projeto", projeto);
        return "pages/panelViewProject";
    }

    // EDITAR (TELA)
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        InfoProject projeto = cardService.buscarPorId(id);
        model.addAttribute("projeto", projeto);
        return "pages/panelEditProject";
    }

    // EDITAR (POST)
    @PostMapping("/editar")
    public String atualizar(InfoProject projeto) {
        cardService.salvar(projeto);
        return "redirect:/panelProjetos";
    }

    // EXCLUIR
    @PostMapping("/{id}/deletar")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try{
            cardService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Projeto excluído com sucesso!");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir o projeto");
        }
        return "redirect:/panelProjetos";
    }
}

