package com.kauan.projex.repository;

import com.kauan.projex.model.InfoProject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<InfoProject, Long>{
    List<InfoProject> findTop3ByOrderByAtualizadoEmDesc();
}
