package com.kauan.projex.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class CadastroUser {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("pageTitle", "Cadastro de Usuário");
        model.addAttribute("usuario", new InfoUser());
        return "pages/cadastro";
    }

    @PostMapping("/usuario/salvarUsuario")
    public String salvarUsuario(
            @Valid @ModelAttribute("usuario") InfoUser usuario,
            BindingResult result,
            Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        // Validação básica de campos obrigatórios
        if (usuario.getName() == null || usuario.getName().isBlank() ||
            usuario.getEmail() == null || usuario.getEmail().isBlank() ||
            usuario.getPassword() == null || usuario.getPassword().isBlank() ||
            usuario.getConfirmPassword() == null || usuario.getConfirmPassword().isBlank() ||
            usuario.getCpf() == null || usuario.getCpf().isBlank() ||
            usuario.getDataNasc() == null) {

            redirectAttributes.addFlashAttribute("mensagemErro", "Todos os campos obrigatórios devem ser preenchidos!");
            return "redirect:/cadastro";
        }

        // Validação das anotações do model
        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            return "pages/cadastro";
        }

        try {
            // Ajuste de campos e tipos compatíveis
            Timestamp agora = new Timestamp(System.currentTimeMillis());

            usuario.setName(usuario.getName().trim());
            usuario.setCpf(usuario.getCpf().replaceAll("\\D", ""));
            usuario.setRole("USER");
            usuario.setAtivo(true);
            usuario.setTentativasLogin(0);

            // Conversão LocalDateTime -> Timestamp
            usuario.setUltimoLogin(Timestamp.valueOf(LocalDateTime.now()));
            usuario.setAtualizadoEm(agora);

            usuario.setForcarTrocaSenha(false);
            usuario.setDescricao("Descrição padrão");
            usuario.setFotoPerfil("default.png");
            usuario.setProjetos(new ArrayList<>());

            // Token de reset
            usuario.setToken(UUID.randomUUID().toString());
            usuario.setResetTokenExpiracao(
                    LocalDateTime.now().plusHours(4).format(DateTimeFormatter.ISO_DATE_TIME)
            );

            // IP
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isBlank()) ip = request.getRemoteAddr();
            usuario.setIpCriacao(ip);
            usuario.setIpUltimoLogin(ip);

            // Salvar usuário no banco
            usuarioRepository.save(usuario);

            redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");
            return "redirect:/home";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar usuário: " + e.getMessage());
            return "redirect:/cadastro";
        }
    }
}
