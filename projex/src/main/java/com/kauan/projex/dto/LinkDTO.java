package com.kauan.projex.dto;

import com.kauan.projex.model.InfoUser;
import com.kauan.projex.utils.TipoLink;

public class LinkDTO {
    private Long id;
    private String url;
    private TipoLink tipoLink;
    private InfoUser usuario;
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
