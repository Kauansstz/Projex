package com.kauan.projex.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kauan.projex.model.InfoProject;

@Repository
public interface CardRepository extends JpaRepository<InfoProject, Long> {
    List<InfoProject> findByTituloContainingIgnoreCase(String titulo);
}
