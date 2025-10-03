package com.kauan.projex.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class InfoUser {
    
    public enum Genero {
        MASCULINO,
        FEMININO,
        OUTRO
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;

    @Pattern(regexp = "\\d{11}", message = "CPF deve conter apenas 11 números")
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;

    @Pattern(regexp = "\\d{10,15}", message = "Telefone deve conter apenas números")
    @Column(length = 15)
    private String telefone;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "dono", fetch = FetchType.LAZY)
    private List<InforProject> projetos;

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getCriadoEm() { return criadoEm; }

    @Override
    public String toString() {
        return "InfoUser{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", genero=" + genero +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", descricao='" + descricao + '\'' +
                ", criadoEm=" + criadoEm +
                '}';
    }
}
