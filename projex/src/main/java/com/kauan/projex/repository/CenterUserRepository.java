package com.kauan.projex.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kauan.projex.model.InfoUser;

@Repository
public interface CenterUserRepository extends JpaRepository<InfoUser, Long>{
    Boolean existsByNameUserContainingIgnoreCase(@Param("nameUser") String nameUser);
    List<InfoUser> findByAtivo(@Param("isAtivo") Boolean isAtivo);
    List<InfoUser> findByNameContainingIgnoreCase(@Param("nome") String nome);
    List<InfoUser> findByNameUserContainingIgnoreCase(@Param("nome") String nome);
}
