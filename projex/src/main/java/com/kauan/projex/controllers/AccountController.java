package com.kauan.projex.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.service.CardService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
public class AccountController {

    private final CardService cardService;

    public AccountController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/panelAccount")
    public String panelAccount(Model model, HttpServletRequest request) {

        InfoUser usuario =
            (InfoUser) request.getSession().getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        List<InfoProject> ultimosProjetos =
            cardService.buscarUltimos3Projetos(usuario);

        model.addAttribute("ultimosProjetos", ultimosProjetos);
        model.addAttribute("pageTitle", "Perfil");

        return "pages/account";
}
}

