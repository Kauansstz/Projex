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
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Service
public class HistoricoService {
    private final HistoricoRepository repository;
    private final Logger logger = LoggerFactory.getLogger(HistoricoService.class);

    // formato legível para datas (se o campo for um Temporal)
    private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public HistoricoService(HistoricoRepository repository) {
        this.repository = repository;
    }

    private String getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth == null ? "" : auth.getName();
    }

    // ---------- CRUD ----------
    @Transactional
    public Historico salvar(String usuario, String acao, String entidade, String entidadeId, String detalhes, String ip) {
        Historico h = new Historico(usuario, acao, entidade, entidadeId, detalhes, ip);
        Historico salvo = repository.save(h);

        logger.info("HISTORICO saved: user={} acao={} entidade={} id={} ip={}",
                usuario, acao, entidade, entidadeId, ip);

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

    // ---------- Regras de negócio ----------
    public boolean acaoEstaVazia(Historico h) {
        if (h == null || h.getAcao() == null) return true;

        String acao = h.getAcao()
                .replaceAll("(?i)<[^>]*>", "") 
                .replace("&nbsp;", " ")
                .replaceAll("[\\u00A0\\u200B\\u200C\\u200D\\uFEFF]", "")
                .trim();

        return acao.isEmpty();
    }

    public boolean semAtualizacao(Historico h) {
        return h == null || h.getUpdateAt() == null || acaoEstaVazia(h);
    }

    public String limparAcao(Historico h) {
        if (h == null || h.getAcao() == null) return "";
        return h.getAcao().trim();
    }

    public List<Historico>buscarUltimasAtividades(int limit){
        return repository.findTop3ByOrderByUpdateAtDesc();
    }
    public String mensagemAtividades(boolean tem){
        if (!tem){ 
        return "<span class='bg-white rounded-lg shadow p-4 hover:bg-gray-50 transition'>Nenhuma atualização</span>";
        }
        return "";
    }

    public String verificarDadosHistory(Historico historico){
        

        StringBuilder body = new StringBuilder();
        body.append("<div class='text-sm text-gray-700'>");

        String acao = limparAcao(historico);
        body.append("<strong>Ação:</strong> ").append(escapeForHtml(acao)).append("<br>");

        String entidade = historico.getEntidade() == null ? "-" : historico.getEntidade();
        body.append("<strong>Entidade:</strong> ").append(escapeForHtml(entidade)).append("<br>");

        // EntidadeId (se existir)
        String entidadeId = historico.getEntidadeId() == null ? "-" : historico.getEntidadeId();
        body.append("<strong>EntidadeId:</strong> ").append(escapeForHtml(entidadeId)).append("<br>");

        // Usuário
        String usuario = historico.getUsuario() == null ? "-" : historico.getUsuario();
        body.append("<strong>Usuário:</strong> ").append(escapeForHtml(usuario)).append("<br>");

        // Última atualização (tratamento para tipos de data)
        Object upd = historico.getUpdateAt();
        String updateAtStr = "-";
        if (upd != null) {
            try {
                if (upd instanceof TemporalAccessor) {
                    updateAtStr = DT_FORMATTER.format((TemporalAccessor) upd);
                } else {
                    updateAtStr = upd.toString();
                }
            } catch (Exception e) {
                updateAtStr = upd.toString();
            }
        }
        body.append("<strong>Última atualização:</strong> ").append(escapeForHtml(updateAtStr));

        body.append("</div>");
        return body.toString().strip();
    }

    private String escapeForHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;");
    }
}
