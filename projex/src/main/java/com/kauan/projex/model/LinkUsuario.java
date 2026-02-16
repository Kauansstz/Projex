package com.kauan.projex.model;

import com.kauan.projex.utils.TipoLink;
import jakarta.persistence.*;

@Entity
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

    // ========================
    // GETTERS AND SETTERS
    // ========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TipoLink getTipoLink() {
        return tipoLink;
    }

    public void setTipoLink(TipoLink tipoLink) {
        this.tipoLink = tipoLink;
    }

    public InfoUser getUsuario() {
        return usuario;
    }

    public void setUsuario(InfoUser usuario) {
        this.usuario = usuario;
    }
}
