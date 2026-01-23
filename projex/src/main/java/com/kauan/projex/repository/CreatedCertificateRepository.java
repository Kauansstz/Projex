package com.kauan.projex.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kauan.projex.model.Certificated;
import com.kauan.projex.utils.Status;

@Repository
public interface CreatedCertificateRepository extends JpaRepository<Certificated, Long> {
    boolean existsByTitulo(String titulo);
    Optional<Certificated> findByTitulo(String titulo);
    Optional<Certificated> findByDescricao(String descricao);
    List<Certificated> findByStatus(Status status);
    List<Certificated> findByDataConclusao(LocalDate dataConclusao); 
}