package com.kauan.projex.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kauan.projex.model.Certificated;
import com.kauan.projex.utils.Status;

@Repository
public interface CreatedCertificateRepository extends JpaRepository<Certificated, Long> {
    boolean existsByTitulo(@Param("titulo") String titulo);
    Optional<Certificated> findByTitulo(@Param("titulo") String titulo);
    Optional<Certificated> findByDescricao(@Param("descricao") String descricao);
    List<Certificated> findByStatus(@Param("status") Status status);
    List<Certificated> findByDataConclusao(@Param("dataConclusao") LocalDate dataConclusao); 
}