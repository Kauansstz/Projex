package com.kauan.projex.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.Tecnologia;
import com.kauan.projex.utils.Genero;

@Setter
@Getter
public class InfoUserRequest {
    
        public InfoUserRequest() {
            this.name = "";
        }
    
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        @Column(name="NOME", length = 100, nullable = false)
        private String name;
    
        @Column(name="DATA_NASC", nullable = false, updatable = false)
        private LocalDate dataNasc;
    
        @Column(name="PASSWORD", nullable = false)
        private String password;
    
        @Column(name="CONFIRM_PASSWORD", nullable = false)
        private String confirmPassword;
    
        @Enumerated(EnumType.STRING)
        @Column(name="GENERO", nullable = false)
        private Genero genero;
    
        @Pattern(regexp = "[\\d\\-().\\s]+", message = "CPF deve conter apenas números")
        @Column(name="CPF", length = 11, nullable = false, unique = true)
        private String cpf;
    
        @Pattern(regexp = "[\\d\\-().\\s]+", message = "Telefone deve conter apenas números")
        @Column(name="TELEFONE", length = 15)
        private String telefone;
    
        @Column(name="DESCRICAO", columnDefinition = "TEXT")
        private String descricao;
    
        @Column(name="CRIADO_EM", nullable = false, updatable = false)
        private Timestamp criadoEm;
    
        @Column(name="ATIVO", nullable = false)
        private Boolean ativo;
    
        @Column(name="ULTIMO_LOGIN", nullable = false)
        private Timestamp ultimoLogin;
    
        @Column(name="ATUALIZADO_EM", nullable = false)
        private Timestamp atualizadoEm;
    
        @Column(name="TENTATIVAS_LOGIN", nullable = false)
        private Integer tentativasLogin;
    
        @Column(name="ROLE", nullable = false)
        private String role;
    
        @Column(name="TOKEN", nullable = false)
        private String token;
    
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="tecnologia_id")
        private Tecnologia tecnologia;    
        
        @Column(name="RESET_TOKEN_EXPIRACAO", nullable = false)
        private String resetTokenExpiracao;
        
        @Column(name="FOTO_PERFIL")
        private MultipartFile  fotoPerfil;
        
        @Column(name="IP_CRIACAO", nullable = false)
        private String ipCriacao;
        
        @Column(name="IP_ULTIMO_LOGIN", nullable = false)
        private String ipUltimoLogin;
        
        @Column(name="FORCAR_TROCA_SENHA", nullable = false)
        private Boolean forcarTrocaSenha;
        
        @Column(name="EMAIL", nullable = false)
        private String email;
        
        @Column(name="ACEITAR_TERMOS", nullable = false)
        private boolean aceitarTermos;
    
        @OneToMany(mappedBy = "dono", fetch = FetchType.LAZY)
        private List<InfoProject> projetos = new ArrayList<>();
        
        public String nameUser;

        public Long inativadoPor;

        public LocalDateTime dataInativacao;
    
        @PrePersist
        protected void onCreate() {
            criadoEm = Timestamp.valueOf(LocalDateTime.now());
        }
        
      
        }
    
    
