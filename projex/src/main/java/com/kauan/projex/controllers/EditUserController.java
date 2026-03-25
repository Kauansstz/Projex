package com.kauan.projex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kauan.projex.dto.EditUserDTO;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.service.EditUserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

@Controller
@RequestMapping("editUser/")
public class EditUserController {
    private final EditUserService service;

    public EditUserController(EditUserService service){
        this.service = service;
    }
    
    @PostMapping("/editar/{id}")
    public String editUser(@PathVariable Long id,
                        @ModelAttribute("user") EditUserDTO formUser,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {

        try {
            service.atualizarComDTO(id, formUser);

            redirectAttributes.addFlashAttribute("mensagemSucesso",
                    "Usuário alterado com sucesso!");

            return "redirect:/panelUser";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("mensagemErro",
                    e.getMessage());

            return "redirect:/panelUser";
        }
    }

    @GetMapping("/editar/{id}")
    public String exibirUser(@PathVariable Long id, Model model){
        InfoUser user= service.buscarPorId(id);
        model.addAttribute("user",user);
        model.addAttribute("pageTitle", "Edição do usuário");
        return "pages/panelEditUser";
    }

}
