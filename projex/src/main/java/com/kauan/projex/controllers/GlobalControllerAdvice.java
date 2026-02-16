package com.kauan.projex.controllers;

import com.kauan.projex.model.InfoUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("usuarioLogado")
    public InfoUser adicionarUsuarioAoModel(HttpSession session) {
        // Busca o objeto que você setou no login manual
        return (InfoUser) session.getAttribute("usuarioLogado");
    }
}