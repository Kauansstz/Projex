package com.kauan.projex.controllers;

import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.TecnologiaRepository;
import com.kauan.projex.service.EditCardService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.stream.Collectors;

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
    public String editProject(@ModelAttribute InfoProject project, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        try{
            String tecnologia = project.getTecnologiasText();
            if (tecnologia != null && !tecnologia.isBlank()) {
                 project.setTecnologiasText(
                    Arrays.stream(tecnologia.split(";"))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .distinct()
                        .collect(Collectors.joining(";"))
                );
                
            }
            InfoUser dono = (InfoUser) request.getSession().getAttribute("usuarioLogado");
            if (dono == null) {
                System.out.println("Usuário não autenticado: " + (dono != null ? dono.getEmail() : "null"));
                throw new WorkFlowException("Usuário não autenticado.");
            }
            System.out.println("Usuário autenticado: " + dono.getEmail());

            project.setDono(dono);
            cardService.infoCardEdit(project);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Card alterado com sucesso!");
            return "redirect:/panelProjetos";
        }catch(Exception e){
            System.out.println("Dentro do Catch");
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/panelProjetos";
        }
    }

    @GetMapping("/{id}/editar")
    public String editarProjeto(@PathVariable Long id, Model model) {

        InfoProject projeto = cardService.buscarPorId(id);

        model.addAttribute("projeto", projeto);
        model.addAttribute("tecnologias", tecnologiaRepository.findAll());
        model.addAttribute("pageTitle", "Editar Projeto");

        return "pages/panelEditProject";
    }
}
