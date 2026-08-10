package com.kauan.projex.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoUser;

@Repository
public interface CardRepository extends JpaRepository<InfoProject, Long> {
    List<InfoProject> findByTituloContainingIgnoreCase(@Param("titulo") String titulo);
    List<InfoProject> findTop2ByDonoOrderByCriadoEmDesc(InfoUser dono);
    List<InfoProject> findByIsPublish(@Param("publish") Boolean publish);
}
