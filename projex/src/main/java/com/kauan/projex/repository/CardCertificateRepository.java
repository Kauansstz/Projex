package com.kauan.projex.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kauan.projex.model.Certificated;
import com.kauan.projex.model.InfoUser;

@Repository
public interface CardCertificateRepository extends JpaRepository<Certificated, Long> {
    List<Certificated> findByTituloContainingIgnoreCase(String titulo);
    List<Certificated> findTop3ByDonoOrderByCriadoEmDesc(InfoUser dono);
}
