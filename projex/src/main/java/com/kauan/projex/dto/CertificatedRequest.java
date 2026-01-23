package com.kauan.projex.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.utils.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

public class CertificatedRequest {

    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String instituicao;

    private String typeCertificate;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private Status status;

    private MultipartFile  anexo;

    private String category;

    private LocalDate dataConclusao;

    private LocalDateTime update = LocalDateTime.now();

    private LocalDateTime criadoEm;

    @ManyToOne
    @JoinColumn(name = "dono_id")
    @JsonIgnore
    private InfoUser dono;

    // GETTERS E SETTERS

    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }
    public String getTypeCertificate() {
        return typeCertificate;
    }

    public void setTypeCertificate(String typeCertificate) {
        this.typeCertificate = typeCertificate;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public MultipartFile  getAnexo() {
        return anexo;
    }

    public void setAnexo(MultipartFile  anexo) {
        this.anexo = anexo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public LocalDateTime getUpdate() {
        return update;
    }

    public void setUpdate(LocalDateTime update) {
        this.update = update;
    }

    public InfoUser getDono() {
        return dono;
    }

    public void setDono(InfoUser dono) {
        this.dono = dono;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
