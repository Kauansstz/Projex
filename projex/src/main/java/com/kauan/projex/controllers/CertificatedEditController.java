package com.kauan.projex.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kauan.projex.dto.CertificatedRequest;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.service.EditCertificateService;
import com.kauan.projex.utils.Category;

import jakarta.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Controller;

@Controller
public class CertificatedEditController {
    private final EditCertificateService certService;
  

    public CertificatedEditController(EditCertificateService certService) {
        this.certService = certService;
    }

    @PostMapping("/{id}/editar")
    public String editProject(@PathVariable Long id ,@ModelAttribute CertificatedRequest certificado, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try{
            certificado.setId(id);
            certService.infoCertificateEdit(certificado);
            
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Card alterado com sucesso!");
            return "redirect:/panelCertificados";
        }catch(WorkFlowException e){
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/panelCertificados/{id}/editar";
        }
    }

    @GetMapping("/{id}/editar")
    public String exibirCertificado(Model model, @PathVariable Long id, @RequestParam(required = false) Category category, RedirectAttributes redirectAttributes){
         try {
            CertificatedRequest certificado = certService.buscarPorId(id);
            model.addAttribute("certificado", certificado);
            model.addAttribute("category", category);
            model.addAttribute("categorias", Category.values());
            model.addAttribute("pageTitle", "Editar Certificado");
            return "pages/panelEditCertificate";
        } catch (WorkFlowException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/panelCertificados";
        }
    }
}
