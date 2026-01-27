package com.kauan.projex.model;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kauan.projex.utils.Category;
import com.kauan.projex.utils.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
@Entity
public class Certificated {
@Id  
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String titulo;

private String instituicao;
private String descricao;

private String  anexo;

@Enumerated(EnumType.STRING)
private Status status;

private String typeCertificate;

@Enumerated(EnumType.STRING)
private Category category;

private LocalDate dataConclusao;

private LocalDateTime update = LocalDateTime.now();

private LocalDateTime criadoEm;


@ManyToOne
@JoinColumn(name = "dono_id")
@JsonIgnore
private InfoUser dono;

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

public String getDescricao() {
    return descricao;
}

public void setDescricao(String descricao) {
    this.descricao = descricao;
}

public String  getAnexo() {
    return anexo;
}

public void setAnexo(String  anexo) {
    this.anexo = anexo;
}

public Status getStatus() {
    return status;
}

public void setStatus(Status status) {
    this.status = status;
}

public String getTypeCertificate() {
    return typeCertificate;
}

public void setTypeCertificate(String typeCertificate) {
    this.typeCertificate = typeCertificate;
}

public Category getCategory() {
    return category;
}

public void setCategory(Category category) {
    this.category = category;
}

public LocalDate getDataConclusao() {
    return dataConclusao;
}

public void setDataConclusao(LocalDate dataConclusao) {
    this.dataConclusao = dataConclusao;
}

public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

public void setCriadoEm(LocalDateTime criadoEm) {
    this.criadoEm = criadoEm;
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

}
