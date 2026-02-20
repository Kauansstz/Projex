package com.kauan.projex.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kauan.projex.dto.PerguntaRequestDTO;
import com.kauan.projex.model.*;
import com.kauan.projex.repository.*;
import com.kauan.projex.utils.Category;

import java.util.List;

@Service
public class PerguntaService {

    private final PerguntaRepository perguntaRepository;
    private final RespostaRepository respostaRepository;

    public PerguntaService(PerguntaRepository perguntaRepository,
                           RespostaRepository respostaRepository) {
        this.perguntaRepository = perguntaRepository;
        this.respostaRepository = respostaRepository;
    }

    @Transactional
    public Pergunta criarPergunta(PerguntaRequestDTO dto) {

        Category categoria = dto.getCategoria();


        Pergunta pergunta = new Pergunta();
        pergunta.setCategoria(categoria);
        pergunta.setEnunciado(dto.getEnunciado());
        pergunta.setTipo(dto.getTipo());
        pergunta.setPontuacao(dto.getPontuacao());
        pergunta.setOrdem(dto.getOrdem());
        pergunta.setAtivo(true);

        Pergunta perguntaSalva = perguntaRepository.save(pergunta);

        if (dto.getRespostas() != null && !dto.getRespostas().isEmpty()) {

            long totalCorretas = dto.getRespostas().stream()
                    .filter(r -> Boolean.TRUE.equals(r.getCorreta()))
                    .count();

            if (totalCorretas == 0) {
                throw new RuntimeException("Pergunta precisa ter ao menos uma resposta correta.");
            }

            dto.getRespostas().forEach(r -> {
                Resposta resposta = new Resposta();
                resposta.setPergunta(perguntaSalva);
                resposta.setDescricao(r.getDescricao());
                resposta.setCorreta(r.getCorreta());
                resposta.setOrdem(r.getOrdem());
                resposta.setAtivo(true);
                respostaRepository.save(resposta);
            });
        }

        return perguntaSalva;
    }

    public List<Pergunta> listarPorCategoria(Category categoriaId) {
        return perguntaRepository.findByCategoriaAndAtivoTrue(categoriaId);
    }
}

