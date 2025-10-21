package com.kauan.projex.controllers;

import com.kauan.projex.dto.HistoricoDTO;
import com.kauan.projex.model.Historico;
import com.kauan.projex.service.HistoricoService;
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

    @PostMapping("/salvar")
    public ResponseEntity<Historico> salvar(@RequestBody HistoricoDTO dto) {
        Historico salvo = service.salvar(dto.getUsuario(), dto.getAcao(), dto.getEntidade(), dto.getEntidadeId(), dto.getDetalhes(), dto.getIp());
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<Historico>> listar(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size) {
        Page<Historico> p = service.listar(PageRequest.of(page, size));
        return ResponseEntity.ok(p);
    }
    @GetMapping("/exportCSV")
    public void exportarHistoricoCsv(HttpServletResponse response) throws IOException{
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=historico.csv");

        List<Historico> historicos;

        if (SecurityContextHolder
        .getContext()
        .getAuthentication().
        getAuthorities().
        stream().
        anyMatch(a-> a.getAuthority().equals("ROLE_ADMIN"))) {
            historicos = service.listarTodos();
        }else{
            historicos = service.listar(PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        }

        try (PrintWriter writer = response.getWriter()){
            writer.println("Usuário;Ação;Entidade;EntidadeId;Detalhes;IP;Data");

            for (Historico log: historicos){
                writer.println(
                    String.format("%s;%s;%s;%s;%s;%s;%s", 
                        log.getUsuario(),
                        log.getAcao(),
                        log.getEntidade(),
                        log.getEntidade(),
                        log.getEntidadeId(),
                        log.getIp(),
                        log.getCreatedAt()
                    )
                );
            }
            writer.flush();
            writer.close();
        }catch (Exception e) {
            logger.error("Erro ao exportar CSV", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao exportar CSV");
        }}

    @GetMapping
    public String exibirHistorico(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<Historico> historicos = service.listar(PageRequest.of(page, size));
        model.addAttribute("historicos", historicos.getContent());
        model.addAttribute("paginaAtual", page);
        model.addAttribute("totalPaginas", historicos.getTotalPages());
        return "pages/historico"; 
}
}