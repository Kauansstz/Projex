package com.kauan.projex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kauan.projex.model.Pergunta;
import com.kauan.projex.utils.Category;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
    boolean existsByCategoria(Category categoria);
    List<Pergunta> findByCategoriaAndAtivoTrue(Category categoria);

}
