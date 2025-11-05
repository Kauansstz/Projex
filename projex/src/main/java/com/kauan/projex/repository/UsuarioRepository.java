package com.kauan.projex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kauan.projex.model.InfoUser;

public interface UsuarioRepository extends JpaRepository<InfoUser, Long> {
    Boolean existsByCpf(String cpf);
    Optional<InfoUser> findByCpf(String cpf);
    InfoUser findByEmail(String email);
}
