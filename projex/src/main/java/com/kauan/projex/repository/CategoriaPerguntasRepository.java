package com.kauan.projex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kauan.projex.model.Categoria;

public interface CategoriaPerguntasRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByAtivoTrue();
}