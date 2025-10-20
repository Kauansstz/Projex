package com.kauan.projex.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class DashboardController {
    @GetMapping("/")
    public String dashboard(Model model){
        model.addAttribute("pageTitle", "Dashboard");
        return "pages/dashboard";
    }
    
}
