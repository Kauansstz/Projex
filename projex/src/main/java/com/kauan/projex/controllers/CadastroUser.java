package com.kauan.projex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CadastroUser {
    @GetMapping("/cadastro")
    public String cadastro(Model model){
        model.addAttribute("pageTitle", "Cadastro de Usuário");
        return "pages/cadastro";
    }
}
