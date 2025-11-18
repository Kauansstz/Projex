package com.kauan.projex.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kauan.projex.model.Historico;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.model.InforProject;
import com.kauan.projex.service.DashBoardService;
import com.kauan.projex.utils.FormatarTempoRelativo;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class DashboardController {
    @Autowired
    private DashBoardService dashBoardService;
    @GetMapping("/home")
    public String dashboard(Model model, HttpServletRequest request) {
        InfoUser usuarioLogado = (InfoUser) request.getSession().getAttribute("usuarioLogado");
        InforProject project = (InforProject) request.getSession().getAttribute("project");
        Historico history = (Historico) request.getSession().getAttribute("history"); 
        String tempo = null;
        if (history != null && history.getUpdateAt() != null) {
            tempo = FormatarTempoRelativo.formatar(history.getUpdateAt());
        }
        String htmlGerado = dashBoardService.validarCamposDashBoard(usuarioLogado, project, history, tempo);
        model.addAttribute("nome", usuarioLogado.getName());
        model.addAttribute("titulo", project != null ? project.getTitulo() : null );
        model.addAttribute("atualizadoEm", project != null ? project.getAtualizadoEm() : null );
        model.addAttribute("status", project != null ? project.getStatus() : null );
        model.addAttribute("usuario", history != null ? history.getUsuario() : null );
        model.addAttribute("acao", history != null ? history.getAcao() : null );
        model.addAttribute("entidade", history != null ? history.getEntidade() : null );
        model.addAttribute("updateAt", history != null ? history.getUpdateAt() : null );

        model.addAttribute("tempoAtualizado", tempo);
        model.addAttribute("project", project);
        model.addAttribute("history", history);
        model.addAttribute("html", htmlGerado); 
        
        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("pageTitle", "Dashboard");
        return "pages/dashboard";
    }
}
