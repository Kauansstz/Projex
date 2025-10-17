package com.kauan.projex.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

import com.kauan.projex.model.utils.Tecnologia;
import jakarta.persistence.*;

@Data
@Entity
public class InforProject {
    public enum Status{
        EM_ANDAMENTO,
        CONCLUIDO,
        CANCELADO
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String titulo;

    @Column(columnDefinition="TEXT")
    private String descricao;

    @ManyToMany
    @JoinTable(
        name = "infoproject_tecnologia",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name= "tecnologia_id")
    )
    private List<Tecnologia> tecnologias;

    private LocalDate dataConclusao;


    private Instant criadoEm;

    private Instant atualizadoEm;

    @PrePersist
    protected void onCreate() {
        criadoEm = Instant.now();
    }
    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = Instant.now();
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private InfoUser dono;


    // Setter e Getter

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public List<Tecnologia> getTecnologia() {
        return tecnologias;
    }

    public void setTecnologia(List<Tecnologia> tecnologias) {
        this.tecnologias = tecnologias;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Instant atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public InfoUser getDono() {
        return dono;
    }

    public void setDono(InfoUser dono) {
        this.dono = dono;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }


    }