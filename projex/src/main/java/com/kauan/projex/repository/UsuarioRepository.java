package com.kauan.projex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kauan.projex.model.InfoUser;

@Repository
public interface UsuarioRepository extends JpaRepository<InfoUser, Long> {
    Boolean existsByCpf(String cpf);
    Optional<InfoUser> findByCpf(String cpf);
    List<InfoUser> findByTecnologiasTextContaining(String tecnologia);
    Optional<InfoUser> findByEmail(String email);
    Optional<InfoUser> findByEmailAndPassword(String email, String password);
    Optional<InfoUser> findByToken(String token);

}
