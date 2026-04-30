package com.kauan.projex.dto;

import java.time.LocalDate;
import com.kauan.projex.utils.Status;


public class EditProjetoDto {
    private String titulo;
    private String descricao;
    private Boolean isPublish;
    private String tecnologiasText;
    private LocalDate dataConclusao;
    private Status status;


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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
}
