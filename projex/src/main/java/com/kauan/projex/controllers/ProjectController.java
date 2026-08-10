package com.kauan.projex.controllers;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.service.CardService;
import jakarta.servlet.http.HttpServletRequest;


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
                         @RequestParam(name = "search",required = false) String search,
                        RedirectAttributes redirectAttributes,
                    HttpServletRequest request) {
        
        InfoUser usuarioLogado =(InfoUser) request.getSession().getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            redirectAttributes.addFlashAttribute("Sessão foi encerrada");
            return "redirect:/login";
        }
        List<InfoProject> isPublish = cardService.buscarPorPublicado(true);
        
        if (search != null && !search.isBlank()) {
            isPublish = cardService.buscarPorTitulo(search);
        } else {
            isPublish = cardService.listarTodos();
        }
        model.addAttribute("cards", isPublish);
        model.addAttribute("search", search);
        model.addAttribute("pageTitle", "Projetos");
        return "pages/projectes";
    }


    // EDITAR (TELA)
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model,RedirectAttributes redirectAttributes,HttpServletRequest request) {
        InfoUser usuarioLogado =(InfoUser) request.getSession().getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            redirectAttributes.addFlashAttribute("Sessão foi encerrada");
            return "redirect:/login";
            
        }
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
    public String excluir(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try{
            cardService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Projeto excluído com sucesso!");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir o projeto");
        }
        return "redirect:/panelProjetos";
    }
}

