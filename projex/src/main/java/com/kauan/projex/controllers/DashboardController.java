package com.kauan.projex.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class DashboardController {

    @GetMapping("/home")
    public String dashboard(Model model, HttpServletRequest request) {
        Object usuarioLogado = request.getSession().getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("usuarioLogado", usuarioLogado); 

        return "pages/dashboard";
    }
}
