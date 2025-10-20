package com.kauan.projex.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AccountController {
    @GetMapping("/panelAccount")
    public String panelAccount(Model model){
        model.addAttribute("pageTitle", "Perfil");
        return "pages/account";
    }
}
