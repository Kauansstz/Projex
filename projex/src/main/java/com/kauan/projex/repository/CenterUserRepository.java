package com.kauan.projex.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.kauan.projex.model.InfoUser;

@Repository
public interface CenterUserRepository extends JpaRepository<InfoUser, Long>{
    Boolean existsByNameUserContainingIgnoreCase(String nameUser);
    List<InfoUser> findByAtivo(Boolean isAtivo);
    List<InfoUser> findByNameContainingIgnoreCase(String nome);
    List<InfoUser> findByNameUserContainingIgnoreCase(String nome);
}
