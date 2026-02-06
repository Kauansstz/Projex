package com.kauan.projex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;

@Controller
public class EditUserController {
    @GetMapping("/centeruser")
    public String exibirUser(Model model){
        model.addAttribute("pageTitle", "Central do usuário");
        return "pages/centerUser";
    }

}
