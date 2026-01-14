package com.kauan.projex.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kauan.projex.service.ExploreService;

import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/explore")
public class ExplorerController {
    public final ExploreService exploreService;

    public  ExplorerController(ExploreService exploreService){
        this.exploreService= exploreService;
    }

    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String search ){
        if (search != null && !search.isBlank()) {
            model.addAttribute("explorer",exploreService.buscarPorNome(search));
            model.addAttribute("tecnologia", exploreService.buscarPorTecnologia(search));
        }else{
            model.addAttribute("explorer", exploreService.listarTodos());
        }
        model.addAttribute("search",search);
        model.addAttribute("pageTitle", "Explorar");
        return "pages/explorer";
    }
 
}
