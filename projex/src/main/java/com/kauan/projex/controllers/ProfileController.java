package com.kauan.projex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.TecnologiaRepository;
import com.kauan.projex.service.InfoUserService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/EditAccount")
public class ProfileController {
    @Autowired
    private TecnologiaRepository tecnologiaRepository;
    
    private InfoUserService service;

    @GetMapping("/{id}/editar")
    public String editPage(Model model,  InfoUser usuario, HttpServletRequest request, RedirectAttributes redirectAttributes) {
       try{

        InfoUser usuarioLogado = (InfoUser) request.getSession().getAttribute("usuarioLogado");
        
        model.addAttribute("usuario", usuarioLogado);
        model.addAttribute("todasTecnologias", tecnologiaRepository.findAll());
        
        return "redirect:/panelEditAccount"; 
    } catch(Exception e){
        redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        return "redirect:/panelAccount";
    } 
}

    @PostMapping("/atualizar") 
    public String updateProfile(@ModelAttribute("usuario") InfoUser formUser,
                                @RequestParam(value = "fileFoto", required = false) MultipartFile file,
                                RedirectAttributes redirectAttributes) {
        try {
            service.atualizarPerfil(formUser, file);
            redirectAttributes.addFlashAttribute("success", "Perfil atualizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace(); 
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar: " + e.getMessage());
        }
        return "redirect:/panelAccount";
    }
}