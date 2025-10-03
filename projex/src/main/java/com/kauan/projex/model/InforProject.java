package com.kauan.projex.model;

import java.time.Instant;
import java.util.List;
import lombok.Data;

import com.kauan.projex.model.utils.Tecnologia;
import com.kauan.projex.model.InfoUser;
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
    private List<Tecnologia> tecnologia;

    private Instant dataConclusao;

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

}