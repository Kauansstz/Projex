package com.kauan.projex.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.utils.Category;

@Repository
public interface CardCertificateRepository extends JpaRepository<Certificated, Long> {

    @Query("""
        SELECT c FROM Certificated c
        WHERE (:titulo IS NULL OR LOWER(c.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')))
        AND (:categoria IS NULL OR c.category = :categoria)
        AND (:isPublish IS NULL OR c.isPublish = :isPublish)
    """)
    List<Certificated> filtrar(
            String titulo,
            Category categoria,
            Boolean isPublish
    );

    List<Certificated> findByTituloContainingIgnoreCase(String titulo);

    List<Certificated> findByCategory(Category categoria);

    List<Certificated> findTop3ByDonoOrderByCriadoEmDesc(InfoUser dono);

    List<Certificated> findByIsPublish(Boolean isPublish);
}
