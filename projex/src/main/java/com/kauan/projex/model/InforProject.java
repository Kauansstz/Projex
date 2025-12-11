package com.kauan.projex.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class InforProject {

    public enum Status {
        EM_ANDAMENTO,
        CONCLUIDO,
        CANCELADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private String tecnologiasText;

    @ManyToMany
    @JoinTable(
        name = "infoproject_tecnologia",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "tecnologia_id")
    )
    private List<Tecnologia> tecnologias;

    private LocalDate dataConclusao;

    private LocalDate criadoEm;
    private LocalDate atualizadoEm;

    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.atualizadoEm = LocalDate.now();
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "dono_id")
    @JsonIgnore
    private InfoUser dono;
}
