package com.kauan.projex.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoProject.Status;

@Repository
public interface CreatedCardRepository extends JpaRepository<InfoProject, Long> {
    boolean existsByTitulo(String titulo);
    Optional<InfoProject> findByTitulo(String titulo);
    Optional<InfoProject> findByDescricao(String descricao);
    List<InfoProject> findByStatus(Status status);
    List<InfoProject> findByDataConclusao(LocalDate dataConclusao); 
}