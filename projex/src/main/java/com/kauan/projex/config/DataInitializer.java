package com.kauan.projex.config;

import com.kauan.projex.model.Pergunta;
import com.kauan.projex.repository.PerguntaRepository;
import com.kauan.projex.utils.Category;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PerguntaRepository perguntaRepository;

    public DataInitializer(PerguntaRepository perguntaRepository) {
        this.perguntaRepository = perguntaRepository;
    }

    @Override
    public void run(String... args) {

        System.out.println("Iniciando DataInitializer...");

        if (perguntaRepository.count() > 0) {
            System.out.println("Banco já possui perguntas. Inicialização ignorada.");
            return;
        }

        List<Pergunta> perguntas = new ArrayList<>();

        // FINANCEIRO
        perguntas.add(criarPergunta(Category.FINANCEIRO, "O que é fluxo de caixa?"));
        perguntas.add(criarPergunta(Category.FINANCEIRO, "O que é capital de giro?"));

        // CONTÁBIL
        perguntas.add(criarPergunta(Category.CONTABIL, "O que é ativo circulante?"));
        perguntas.add(criarPergunta(Category.CONTABIL, "O que é passivo?"));

        // JURÍDICO
        perguntas.add(criarPergunta(Category.JURIDICO, "O que caracteriza um contrato válido?"));
        perguntas.add(criarPergunta(Category.JURIDICO, "O que é responsabilidade civil?"));

        // RH
        perguntas.add(criarPergunta(Category.RH, "Qual a importância do recrutamento e seleção?"));
        perguntas.add(criarPergunta(Category.RH, "O que é avaliação de desempenho?"));

        // TI
        perguntas.add(criarPergunta(Category.HARDWARE, "O que é memória RAM?"));
        perguntas.add(criarPergunta(Category.SOFTWARE, "O que é um sistema operacional?"));
        perguntas.add(criarPergunta(Category.REDES, "O que é um endereço IP?"));
        perguntas.add(criarPergunta(Category.SEGURANCA_DA_INFORMACAO, "O que é criptografia?"));

        // MARKETING
        perguntas.add(criarPergunta(Category.MARKETING, "O que é funil de vendas?"));
        perguntas.add(criarPergunta(Category.MIDIAS_SOCIAIS, "O que é engajamento digital?"));

        // COMERCIAL
        perguntas.add(criarPergunta(Category.VENDAS, "O que é ticket médio?"));
        perguntas.add(criarPergunta(Category.POS_VENDA, "Qual a importância do pós-venda?"));

        // LOGÍSTICA
        perguntas.add(criarPergunta(Category.LOGISTICA, "O que é gestão de estoque?"));
        perguntas.add(criarPergunta(Category.MANUTENCAO, "O que é manutenção preventiva?"));

        perguntaRepository.saveAll(perguntas);

        System.out.println("Perguntas criadas com sucesso! Total: " + perguntas.size());
    }

    private Pergunta criarPergunta(Category categoria, String enunciado) {
        Pergunta pergunta = new Pergunta();
        pergunta.setCategoria(categoria);
        pergunta.setEnunciado(enunciado);
        pergunta.setAtivo(true);
        return pergunta;
    }
}
