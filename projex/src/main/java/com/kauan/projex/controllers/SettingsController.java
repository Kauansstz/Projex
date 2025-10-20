package com.kauan.projex.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SettingsController {
    @GetMapping("/settings")
    public String settings(Model model){
        model.addAttribute("pageTitle", "Configurações");
        return "pages/settings";
    }
}
