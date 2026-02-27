package com.kauan.projex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kauan.projex.model.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    List<Resposta> findByPerguntaId(Long perguntaId);
}

