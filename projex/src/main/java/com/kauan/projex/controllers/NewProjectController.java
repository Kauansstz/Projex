package com.kauan.projex.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import com.kauan.projex.repository.TecnologiaRepository;
import com.kauan.projex.service.CreatedCardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.kauan.projex.exceptions.DuplicateException;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.model.InfoProject;


@Controller
public class NewProjectController {
    private final TecnologiaRepository tecnologiaRepository;
    private final CreatedCardService repositoryService;


    public NewProjectController(TecnologiaRepository tecnologiaRepository, CreatedCardService repositoryService) {
        this.tecnologiaRepository = tecnologiaRepository;
        this.repositoryService = repositoryService;

    }


    @PostMapping("/projetos")
    public String salvarProjeto(@ModelAttribute InfoProject projeto,  HttpServletRequest request,RedirectAttributes redirectAttributes) {
        System.out.println("Fora do Try");
        try{ 
        repositoryService.salvar(projeto);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Projeto salvo com sucesso!");
        return "redirect:/createdProject";
    } catch (IllegalArgumentException e){
        redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        return "redirect:/createdProject";
    } catch (DuplicateException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/createdProject";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Aconteceu um erro inesperado");
            return "redirect:/createdProject";
        }
}



    @PostMapping("/projetos/createdCard")
    public String cadastrarCard(@ModelAttribute InfoProject project,
                                HttpServletRequest request, 
                                Model model, 
                                RedirectAttributes redirectAttributes){
        try{
            HttpSession session = request.getSession(false);
            System.out.println("Card Session ID: " + (session != null ? session.getId(): "Sem sessão"));
            String tecnologiasText = project.getTecnologiasText();  
            if (tecnologiasText != null) {
                String texto = project.getTecnologiasText();
                System.out.println("DEBUG FRONT: " + texto);
                List<String> listaTecnologias = Arrays.stream(tecnologiasText.split(";")).map(String::trim).filter(s -> !s.isEmpty()).distinct()
                .toList();
                project.setTecnologiasText(String.join(";", listaTecnologias));
            }
            InfoUser dono = (InfoUser) request.getSession().getAttribute("usuarioLogado");
            if (dono == null) {
                System.out.println("Usuário não autenticado: " + (dono != null ? dono.getEmail() : "null"));
                throw new WorkFlowException("Usuário não autenticado.");
            }
            System.out.println("Usuário autenticado: " + dono.getEmail());

            project.setDono(dono);
            repositoryService.infoCard(project);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Card cadastrado com sucesso!");
            return "redirect:/panelProjetos";

        } catch(Exception e){
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/createdProject";
        }
    }

    @GetMapping("/createdProject")
    public String exibirCard(Model model){
        model.addAttribute("projeto", new InfoProject());
        model.addAttribute("tecnologias", tecnologiaRepository.findAll());
        model.addAttribute("pageTitle", "Cadastrar Projeto");
        return "pages/newProject";
    }
}
