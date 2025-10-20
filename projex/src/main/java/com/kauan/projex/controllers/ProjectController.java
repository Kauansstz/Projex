package com.kauan.projex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProjectController {
    @GetMapping("/panelProjetos")
    public String panelProject(Model model){
        model.addAttribute("pageTitle", "Projetos");
        return "pages/projectes";
    }
}
