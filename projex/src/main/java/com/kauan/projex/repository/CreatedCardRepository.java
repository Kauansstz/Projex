package com.kauan.projex.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kauan.projex.model.InforProject;
import com.kauan.projex.model.InforProject.Status;

@Repository
public interface CreatedCardRepository extends JpaRepository<InforProject, Long> {
    boolean existsByTitulo(String titulo);
    Optional<InforProject> findByTitulo(String titulo);
    Optional<InforProject> findByDescricao(String descricao);
    List<InforProject> findByStatus(Status status);
    List<InforProject> findByDataConclusao(LocalDate dataConclusao); 
}