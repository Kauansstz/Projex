package com.kauan.projex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String base(){
        return "pages/base.html";
    }
    @GetMapping("/projetos")
    public String project(){
        return "pages/projectes.html";
    }
}
