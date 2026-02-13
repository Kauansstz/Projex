package com.kauan.projex.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kauan.projex.exceptions.DuplicateException;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CadastroService {

    @Autowired
    private UsuarioRepository repository;

    public InfoUser infoColaboradores(InfoUser usuario, HttpServletRequest request){
         if (usuario.getName() == null || usuario.getName().isBlank() ||
            usuario.getEmail() == null || usuario.getEmail().isBlank() ||
            usuario.getPassword() == null || usuario.getPassword().isBlank() ||
            usuario.getConfirmPassword() == null || usuario.getConfirmPassword().isBlank() ||
            usuario.getCpf() == null || usuario.getCpf().isBlank() ||
            usuario.getDataNasc() == null) {

            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos!");
        }

        // Verificação de aceitação de termos
        if (!usuario.getAceitarTermos()) {
            throw new IllegalArgumentException("Você precisa aceitar os Termos de Uso.");
        }
        

        // Verificação de CPF duplicado
        if (repository.existsByCpf(usuario.getCpf())) {
            throw new DuplicateException("CPF já cadastrado: ", usuario.getCpf());
        }


        // Captura do IP
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank()) {
            ip = request.getRemoteAddr();
        }
        return salvarUsuario(usuario, ip);
    }
    public InfoUser salvarUsuario(InfoUser usuario, String ip) {
        LocalDateTime agora = LocalDateTime.now();
        usuario.setName(usuario.getName().trim());
        usuario.setCpf(usuario.getCpf().replaceAll("\\D", ""));
        usuario.setRole("USER");
        usuario.setAtivo(true);
        usuario.setTentativasLogin(0);
        usuario.setUltimoLogin(LocalDateTime.now());
        usuario.setAtualizadoEm(agora);
        usuario.setForcarTrocaSenha(false);
        usuario.setDescricao("Descrição padrão");
        usuario.setFotoPerfil("default.png");
        usuario.setProjetos(new ArrayList<>());
        usuario.setToken(UUID.randomUUID().toString());
        usuario.setResetTokenExpiracao(LocalDateTime.now().plusHours(4).format(DateTimeFormatter.ISO_DATE_TIME));
        usuario.setIpCriacao(ip);
        usuario.setIpUltimoLogin(ip);

        return repository.save(usuario);
    }
}
