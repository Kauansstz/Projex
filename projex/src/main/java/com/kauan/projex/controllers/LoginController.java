package com.kauan.projex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import com.kauan.projex.exceptions.WorkFlowException;
@Controller
public class LoginController {
    @Autowired
    private UserService usuarioService;
    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("usuarioLogin")) {
            model.addAttribute("usuarioLogin", new InfoUser());
        }
        
        model.addAttribute("pageTitle", "Login");
        return "pages/login";
    }
    
    @PostMapping("/login/Account")
    public String loginPages(@RequestParam String email, 
                            @RequestParam String password, 
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest request) {

        try {
            InfoUser user = usuarioService.authenticator(email, password);
            System.out.println(user);
            request.getSession().setAttribute("usuarioLogado", user);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Login realizado com sucesso!");
            return "redirect:/home";

        } catch (IllegalArgumentException | WorkFlowException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            System.out.println("Teste");
            return "redirect:/login";
        }
    }


}
