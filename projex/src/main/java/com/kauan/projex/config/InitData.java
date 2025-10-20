package com.kauan.projex.config;

import com.kauan.projex.service.HistoricoService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitData {

    private final HistoricoService historicoService;

    public InitData(HistoricoService historicoService) {
        this.historicoService = historicoService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        historicoService.salvar("system", "INIT", "Aplicacao", null, "Dados iniciais carregados", "127.0.0.1");
    }
}
