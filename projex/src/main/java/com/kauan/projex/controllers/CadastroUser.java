package com.kauan.projex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import com.kauan.projex.exceptions.DuplicateException;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.service.CadastroService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class CadastroUser {
    @Autowired
    private final CadastroService cadastroService;

    @GetMapping("/cadastro")
    public String exibirFormulario(Model model) {
        model.addAttribute("usuario", new InfoUser());
        return "pages/cadastro"; 
    } 
    public CadastroUser(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }
    @PostMapping("/usuario/salvarUsuario")
    public String cadastrarUsuario(@ModelAttribute InfoUser usuario, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            cadastroService.infoColaboradores(usuario, request);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/cadastro";
        } catch (DuplicateException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/cadastro";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro inesperado ao cadastrar usuário.");
            return "redirect:/cadastro";
        }
    }
}