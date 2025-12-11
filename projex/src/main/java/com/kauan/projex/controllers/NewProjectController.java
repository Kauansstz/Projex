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
import com.kauan.projex.repository.UsuarioRepository;
import com.kauan.projex.service.CreatedCardService;

import jakarta.servlet.http.HttpServletRequest;
import com.kauan.projex.exceptions.DuplicateException;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.model.InforProject;


@Controller
public class NewProjectController {
    private final TecnologiaRepository tecnologiaRepository;
    private final CreatedCardService repositoryService;
    private final UsuarioRepository user;

    public NewProjectController(TecnologiaRepository tecnologiaRepository, CreatedCardService repositoryService, UsuarioRepository user) {
        this.tecnologiaRepository = tecnologiaRepository;
        this.repositoryService = repositoryService;
        this.user = user;
    }


    @PostMapping("/projetos")
    public String salvarProjeto(@ModelAttribute InforProject projeto,  HttpServletRequest request,RedirectAttributes redirectAttributes) {
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
    public String cadastrarCard(@ModelAttribute InforProject project,
                                HttpServletRequest request, 
                                Model model, 
                                RedirectAttributes redirectAttributes){
        System.out.println("Fora do Try do Cadastrar Card");
        System.out.println("Request: " + request);
        System.out.println("");
        System.out.println("Model: " + model);
        System.out.println("");
        System.out.println("Redirect Attributes: " + redirectAttributes);
        try{

            String tecnologiasText = project.getTecnologiasText();
            if (tecnologiasText != null) {
                String texto = project.getTecnologiasText();
                
                System.out.println("DEBUG FRONT: " + texto);
                List<String> listaTecnologias = Arrays.stream(tecnologiasText.split(";"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .toList();
                
                project.setTecnologiasText(String.join(";", listaTecnologias));
                
            }
            String emailLogado = (String) request.getSession().getAttribute("email");
            InfoUser dono = user.findByEmail(emailLogado)
            .orElseThrow(() -> new WorkFlowException("Usuário não encontrado."));
            project.setDono(dono);
            System.out.println("Debug emailLogado: " + emailLogado);
            repositoryService.infoCard(project);
            return "redirect:/dashboard";

        } catch(Exception e){
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/createdProject";
        }
    }

    @GetMapping("/createdProject")
    public String exibirCard(Model model){
        model.addAttribute("projeto", new InforProject());
        model.addAttribute("tecnologias", tecnologiaRepository.findAll());
        model.addAttribute("pageTitle", "Cadastrar Projeto");
        return "pages/newProject";
    }
}
