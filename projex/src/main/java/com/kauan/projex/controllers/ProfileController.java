package com.kauan.projex.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.model.LinkUsuario;
import com.kauan.projex.repository.TecnologiaRepository;
import com.kauan.projex.service.InfoUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/editAccount")
public class ProfileController {
    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    @Autowired
    private InfoUserService service;

    @GetMapping("/{id}/editar")
    public String editPage(@PathVariable Long id, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            InfoUser usuarioParaEditar = service.getUserWithLinks(id);
    
            // Se ele não tiver links, adicionamos um vazio para o formulário aparecer
            if (usuarioParaEditar.getLink() == null || usuarioParaEditar.getLink().isEmpty()) {
                usuarioParaEditar.getLink().add(new LinkUsuario()); 
            }         
            model.addAttribute("usuario", usuarioParaEditar);
            model.addAttribute("tipoLink", List.of("GITHUB", "LINKEDIN", "PORTFOLIO"));
            model.addAttribute("todasTecnologias", tecnologiaRepository.findAll());          
            return "pages/panelEditAccount"; 

        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao carregar perfil: " + e.getMessage());
            return "redirect:/panelAccount";
        } 
    }

    @PostMapping("/{id}/atualizar") 
    public String updateProfile(@PathVariable Long id,@ModelAttribute("usuario") InfoUser formUser,
                                @RequestParam(value = "fileFoto", required = false) MultipartFile file,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            formUser.setId(id);
            if (formUser.getTecnologiasText() != null && !formUser.getTecnologiasText().isEmpty()) {
            String limpo = Arrays.stream(formUser.getTecnologiasText().split(";"))
                                 .map(String::trim)
                                 .filter(s -> !s.isEmpty())
                                 .distinct()
                                 .collect(Collectors.joining(";"));
            formUser.setTecnologiasText(limpo);
        }
           service.atualizarPerfil(formUser, file);
            InfoUser usuarioNoBanco = service.buscarPorId(id);
            session.setAttribute("usuarioLogado", usuarioNoBanco);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Perfil atualizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace(); 
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao atualizar: " + e.getMessage());
        }
        return "redirect:/panelAccount";
    }
}