package com.kauan.projex.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kauan.projex.dto.PerguntaRequestDTO;
import com.kauan.projex.repository.PerguntaRepository;
import com.kauan.projex.service.PerguntaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.List;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final PerguntaService perguntaService;
    private final PerguntaRepository perguntaRepository;
    private final ObjectMapper objectMapper;

    public DataInitializer(PerguntaService perguntaService,
                        PerguntaRepository perguntaRepository,
                        ObjectMapper objectMapper) {
        this.perguntaService = perguntaService;
        this.perguntaRepository = perguntaRepository;
        this.objectMapper = objectMapper;
    }

    @Override
public void run(String... args) throws Exception {

    // Se já tiver dados, não carrega novamente
    if (perguntaRepository.count() > 0) {
        return;
    }

    InputStream inputStream =
            new ClassPathResource("json/quests.json").getInputStream();

    List<PerguntaRequestDTO> perguntas =
            objectMapper.readValue(inputStream,
                    new TypeReference<List<PerguntaRequestDTO>>() {});

    for (PerguntaRequestDTO dto : perguntas) {
        perguntaService.criarPergunta(dto);
    }

    System.out.println("Perguntas carregadas via JSON!");
}
}