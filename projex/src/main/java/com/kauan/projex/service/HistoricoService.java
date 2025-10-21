package com.kauan.projex.service;

import com.kauan.projex.model.Historico;
import com.kauan.projex.repository.HistoricoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HistoricoService {
    private final HistoricoRepository repository;
    private final Logger logger = LoggerFactory.getLogger(HistoricoService.class);
    private String getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    public HistoricoService(HistoricoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Historico salvar(String usuario, String acao, String entidade, String entidadeId, String detalhes, String ip) {
        Historico h = new Historico(usuario, acao, entidade, entidadeId, detalhes, ip);
        Historico salvo = repository.save(h);

        // também registra no log da aplicação (arquivo/console) — útil pra auditoria rápida
        logger.info("HISTORICO saved: user={} acao={} entidade={} id={} ip={}", usuario, acao, entidade, entidadeId, ip);

        return salvo;
    }

    @Transactional(readOnly = true)
    public Page<Historico> listar(Pageable pageable){
        String usuarioAtual = getUsuarioLogado();
        return repository.findByUsuarioContainingIgnoreCase(usuarioAtual, pageable);
    }

    @Transactional(readOnly = true)
    public List<Historico> listarTodos() {
        return repository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Page<Historico> buscarPorUsuario(String usuario, Pageable pageable) {
        return repository.findByUsuarioContainingIgnoreCase(usuario, pageable);
    }
}
