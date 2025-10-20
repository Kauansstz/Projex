package com.kauan.projex.repository;

import com.kauan.projex.model.Historico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoRepository extends JpaRepository<Historico, Long>{
    
    Page<Historico> findByUsuarioContainingIgnoreCase(String usuario, Pageable pageable);
    Page<Historico> findByAcao(String acao, Pageable pageable);
}
