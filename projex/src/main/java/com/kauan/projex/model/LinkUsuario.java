package com.kauan.projex.model;

import lombok.Getter;
import lombok.Setter;

import com.kauan.projex.utils.TipoLink;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class LinkUsuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @Enumerated(EnumType.STRING)
    private TipoLink tipoLink;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private InfoUser usuario;


}
