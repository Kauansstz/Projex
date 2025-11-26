package com.kauan.projex.controllers;

import com.kauan.projex.model.Historico;
import com.kauan.projex.service.HistoricoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/historico/api/v1")
public class HistoricoController {

    private static final Logger logger = LoggerFactory.getLogger(HistoricoController.class);
    private final HistoricoService service;

    public HistoricoController(HistoricoService service) {
        this.service = service;
    }

    //      SALVAR HISTÓRICO
    @PostMapping("/salvar")
    public ResponseEntity<Historico> salvar(@RequestBody Historico dto, HttpServletRequest request) {

        Historico salvo = service.salvar(
                dto.getUsuario(),
                dto.getAcao(),
                dto.getEntidade(),
                dto.getEntidadeId(),
                dto.getDetalhes(),
                dto.getIp()
        );

        //  Salva o último histórico na sessão (ESSENCIAL PARA FUNCIONAR NO DASHBOARD)
        request.getSession().setAttribute("history", salvo);

        return ResponseEntity.ok(salvo);
    }

    //      LISTAR HISTÓRICO
    @GetMapping("/listar")
    public ResponseEntity<Page<Historico>> listar(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size) {
        Page<Historico> p = service.listar(PageRequest.of(page, size));
        return ResponseEntity.ok(p);
    }

    //      EXPORTAR CSV
    @GetMapping("/exportCSV")
    public void exportarHistoricoCsv(HttpServletResponse response) throws IOException {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=historico.csv");

        List<Historico> historicos;

        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            historicos = service.listarTodos();
        } else {
            historicos = service.listar(PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        }

        try (PrintWriter writer = response.getWriter()) {
            writer.println("Usuário;Ação;Entidade;EntidadeId;Detalhes;IP;Data");

            for (Historico log : historicos) {
                writer.println(
                        String.format("%s;%s;%s;%s;%s;%s;%s",
                                log.getUsuario(),
                                log.getAcao(),
                                log.getEntidade(),
                                log.getEntidadeId(),
                                log.getDetalhes(),
                                log.getIp(),
                                log.getCreatedAt()
                        )
                );
            }

            writer.flush();

        } catch (Exception e) {
            logger.error("Erro ao exportar CSV", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao exportar CSV");
        }
    }

    //  EXIBIR PÁGINA (UI)
    @GetMapping
    public String exibirHistorico(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request,
            Model model) {

        Page<Historico> historicos = service.listar(PageRequest.of(page, size));

        model.addAttribute("historicos", historicos.getContent());
        model.addAttribute("paginaAtual", page);
        model.addAttribute("totalPaginas", historicos.getTotalPages());

        Historico historicoAcao =
                (Historico) request.getSession().getAttribute("history");

        System.out.println(">>>> HISTORY NA SESSÃO: " + historicoAcao);

        model.addAttribute("acaoHistory",
                historicoAcao != null ? historicoAcao.getAcao() : "");

        model.addAttribute("userHistory",
                historicoAcao != null ? historicoAcao.getUsuario() : "");

        model.addAttribute("updateHistory",
                historicoAcao != null ? historicoAcao.getUpdateAt() : "");

        model.addAttribute("entidade",
                historicoAcao != null ? historicoAcao.getEntidade() : "");

        // HTML custom baseado nos dados
        String html = service.verificarDadosHistory(historicoAcao);
        model.addAttribute("htmlHistory", html != null ? html : "");

        return "pages/historico";
    }
}
