package com.kauan.projex.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class InfoProject {

    public enum Status {
        EM_ANDAMENTO("text-yellow-500", "Em andamento"),
        CONCLUIDO("text-green-500", "Concluído"),
        CANCELADO("text-red-500", "Cancelado");

        private final String label;
        private final String cssClass;
        Status(String cssClass, String label){
            this.cssClass = cssClass;
            this.label = label;
        }

        public String getCssClass(){
            return cssClass;
        }
        public String getLabel(){
            return label;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private Boolean isPublish;

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


    public String getTecnologiasText() {
    return tecnologiasText;
    }

    public void setTecnologiasText(String tecnologiasText) {
        this.tecnologiasText = tecnologiasText;
    }

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
    public Boolean getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Boolean isPublish) {
        this.isPublish = isPublish;
    }
    public List<Tecnologia> getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(List<Tecnologia> tecnologias) {
        this.tecnologias = tecnologias;
    }

    public LocalDate getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDate criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDate getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDate atualizadoEm) {
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
    public Long getId() {
    return id;
}

    public void setId(Long id) {
        this.id = id;
    }


    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
}