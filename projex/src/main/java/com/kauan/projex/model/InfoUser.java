package com.kauan.projex.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Pattern;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class InfoUser {

    public InfoUser() {
        this.name = "";
    }

    public enum Genero {
        MASCULINO, FEMININO, OUTRO
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

    @Column(name="RESET_TOKEN_EXPIRACAO", nullable = false)
    private String resetTokenExpiracao;

    @Column(name="FOTO_PERFIL")
    private String fotoPerfil;

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

    @PrePersist
    protected void onCreate() {
        criadoEm = Timestamp.valueOf(LocalDateTime.now());
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { 
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O campo 'Nome' não pode estar vazio");
        }
        this.name = name;
    }

    public LocalDate getDataNasc() { return dataNasc; }
    public void setDataNasc(LocalDate dataNasc) { this.dataNasc = dataNasc; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { 
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O campo 'CPF' não pode estar vazio");
        }
        this.cpf = cpf.replaceAll("\\D", "");
    }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Timestamp getCriadoEm() { return criadoEm; }
    public void setCriadoEm(Timestamp criadoEm) { this.criadoEm = criadoEm; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Timestamp getUltimoLogin() { return ultimoLogin; }
    public void setUltimoLogin(Timestamp ultimoLogin) { this.ultimoLogin = ultimoLogin; }

    public Timestamp getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(Timestamp atualizadoEm) { this.atualizadoEm = atualizadoEm; }

    public Integer getTentativasLogin() { return tentativasLogin; }
    public void setTentativasLogin(Integer tentativasLogin) { this.tentativasLogin = tentativasLogin; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getResetTokenExpiracao() { return resetTokenExpiracao; }
    public void setResetTokenExpiracao(String resetTokenExpiracao) { this.resetTokenExpiracao = resetTokenExpiracao; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

    public String getIpCriacao() { return ipCriacao; }
    public void setIpCriacao(String ipCriacao) { this.ipCriacao = ipCriacao; }

    public String getIpUltimoLogin() { return ipUltimoLogin; }
    public void setIpUltimoLogin(String ipUltimoLogin) { this.ipUltimoLogin = ipUltimoLogin; }

    public Boolean getForcarTrocaSenha() { return forcarTrocaSenha; }
    public void setForcarTrocaSenha(Boolean forcarTrocaSenha) { this.forcarTrocaSenha = forcarTrocaSenha; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public List<InfoProject> getProjetos() { return projetos; }
    public void setProjetos(List<InfoProject> projetos) { this.projetos = projetos; }
    
    public Boolean getAceitarTermos() { return aceitarTermos; }
    public void setAceitarTermos(Boolean aceitarTermos) { this.aceitarTermos = aceitarTermos; }

    @Override
    public String toString() {
        return "InfoUser{" +
                "\nID: " + id +
                " | NOME: '" + name + '\'' +
                " | E-MAIL: '" + email + '\'' +
                " | ATIVO: " + ativo +
                " | ROLE: '" + role + '\'' +
                " | GENERO: " + genero +
                " | CPF: '" + cpf + '\'' + "\n" +
                '}';
    }
}
