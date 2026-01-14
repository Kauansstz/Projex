package com.kauan.projex.repository;

import org.springframework.stereotype.Repository;
import com.kauan.projex.model.InfoUser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ExploreRepository extends JpaRepository<InfoUser, Long>{
    List<InfoUser> findByNameContainingIgnoreCase(String name);
}
