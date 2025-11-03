package com.kauan.projex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kauan.projex.model.InfoUser;

public interface UsuarioRepository extends JpaRepository<InfoUser, Long> {
    InfoUser findByEmail(String email);
}
