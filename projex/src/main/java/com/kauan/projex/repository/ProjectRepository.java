package com.kauan.projex.repository;

import com.kauan.projex.model.InforProject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<InforProject, Long>{
    List<InforProject> findTop3ByOrderByAtualizadoEmDesc();
}
