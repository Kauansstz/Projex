package com.kauan.projex.model;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kauan.projex.utils.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private String anexo;

    private Status status;

    private String typeCertificate;

    private String category;

    private LocalDateTime dataConclusao = LocalDateTime.now();

    private LocalDateTime update = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "dono_id")
    @JsonIgnore
    private InfoUser dono;

}
