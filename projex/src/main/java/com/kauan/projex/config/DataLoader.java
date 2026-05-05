package com.kauan.projex.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kauan.projex.model.Pergunta;
import com.kauan.projex.model.Resposta;
import com.kauan.projex.repository.PerguntaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final PerguntaRepository repository;
    private final ObjectMapper objectMapper; // O Jackson que o vídeo menciona

    public DataLoader(PerguntaRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Limpa o banco para evitar duplicados ao reiniciar
        repository.deleteAll();

        // 2. Tenta ler o ficheiro JSON da pasta resources/data/
        try (InputStream inputStream = getClass().getResourceAsStream("/json/quests.json")) {
            if (inputStream == null) {
                return;
            }

            // 3. O "Pulo do Gato" do vídeo: Converte o JSON diretamente numa lista de Perguntas
            List<Pergunta> perguntas = objectMapper.readValue(inputStream, new TypeReference<List<Pergunta>>(){});

            for (Pergunta pergunta : perguntas) {
                if (pergunta.getRespostas() != null) {
                    for (Resposta resposta : pergunta.getRespostas()) {
                        resposta.setPergunta(pergunta); // IMPORTANTE: Vincula o "filho" ao "pai"
                    }
                }
            }
            repository.saveAll(perguntas);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}