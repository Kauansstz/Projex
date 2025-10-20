package com.kauan.projex.controllers;

import com.kauan.projex.dto.HistoricoDTO;
import com.kauan.projex.model.Historico;
import com.kauan.projex.service.HistoricoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/historico")
public class HistoricoController {

    private final HistoricoService service;

    public HistoricoController(HistoricoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Historico> salvar(@RequestBody HistoricoDTO dto) {
        Historico salvo = service.salvar(dto.getUsuario(), dto.getAcao(), dto.getEntidade(), dto.getEntidadeId(), dto.getDetalhes(), dto.getIp());
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<Page<Historico>> listar(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size) {
        Page<Historico> p = service.listar(PageRequest.of(page, size));
        return ResponseEntity.ok(p);
    }
}
