package com.kauan.projex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kauan.projex.model.utils.Tecnologia;

@Repository
public interface TecnologiaRepository extends JpaRepository<Tecnologia, Long> {
    // Aqui você pode colocar métodos personalizados se quiser, ex:
    // List<Tecnologia> findByNomeContaining(String nome);
}
