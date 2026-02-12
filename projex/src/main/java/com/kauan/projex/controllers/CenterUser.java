package com.kauan.projex.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.CenterUserRepository;
import com.kauan.projex.service.CenterUserService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/panelUser")
public class CenterUser {
    private final CenterUserService service;
    private final CenterUserRepository repository;

    public CenterUser(CenterUserService service, CenterUserRepository repository){
        this.service= service;
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String search){
        List<InfoUser> isAtivo = service.buscarPorAtivo(true);
        if (search != null && !search.isBlank()) {
            isAtivo = service.buscarPorNome(search);
            if (isAtivo.isEmpty()) {
                model.addAttribute("mensagemErro", "Nenhum resultado com: " + search);
            }
        }else{
            isAtivo = service.ListarAllUsers();
        }
        model.addAttribute("usuario", isAtivo);
        model.addAttribute("search",search);
        model.addAttribute("pageTitle", "Painel do Usuário");
        return "pages/panelUser";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        InfoUser user = service.buscarPorId(id);
        model.addAttribute("user", user);
        return "pages/panelEditUser";
    }
    
    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id, InfoUser user){
        user.setId(id);
        service.salvar(user);
        return "redirect:/panelUser";
    }

    
}
