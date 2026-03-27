package com.kauan.projex.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.service.CardCertificateService;
import com.kauan.projex.service.CardService;
import com.kauan.projex.service.ExploreService;
import java.util.List;

import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/explore")
public class ExplorerController {
    public final ExploreService exploreService;
    public final CardService cardService;
    public final CardCertificateService certificateService;
    
    public  ExplorerController(ExploreService exploreService, CardCertificateService certificateService, CardService cardService ){
        this.exploreService= exploreService;
        this.certificateService= certificateService;
        this.cardService= cardService;
    }

    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String search ){
        if (search != null && !search.isBlank()) {
            model.addAttribute("explorer",exploreService.buscarPorNome(search));
            model.addAttribute("tecnologia", exploreService.buscarPorTecnologia(search));
        }else{
            model.addAttribute("explorer", exploreService.listarTodos());
        }
        List<Certificated> certificados = certificateService.buscarPublish(true);
        List<InfoProject> projetos = cardService.buscarPorPublicado(true);

        model.addAttribute("projetos",projetos);
        model.addAttribute("certificados",certificados);
        model.addAttribute("search",search);
        model.addAttribute("pageTitle", "Explorar");
        return "pages/explorer";
    }
 
}
