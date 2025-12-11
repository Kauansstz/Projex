package com.kauan.projex.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InforProjectRequest {

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "As tecnologias são obrigatórias")
    private String tecnologiasText;

    @NotNull(message = "A data de conclusão é obrigatória")
    private LocalDate dataConclusao;

    @NotBlank(message = "O status é obrigatório")
    private String status; // receberá EM_ANDAMENTO, CONCLUIDO, CANCELADO

    @NotNull(message = "O responsável é obrigatório")
    private Long donoId;

    // Getters e Setters

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

    public String getTecnologiasText() {
        return tecnologiasText;
    }

    public void setTecnologiasText(String tecnologiasText) {
        this.tecnologiasText = tecnologiasText;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDonoId() {
        return donoId;
    }

    public void setDonoId(Long donoId) {
        this.donoId = donoId;
    }

    public String getDescripcion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDescripcion'");
    }
}
