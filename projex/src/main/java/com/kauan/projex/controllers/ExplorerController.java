package com.kauan.projex.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ExplorerController {
    @GetMapping("/explore")
    public String explore(Model model){
        model.addAttribute("pageTitle", "Explorar");
        return "pages/explorer";
    }
}
