package com.kauan.projex.dto;

import com.kauan.projex.model.InfoUser;
import com.kauan.projex.utils.TipoLink;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkDTO {
    private Long id;
    private String url;
    private TipoLink tipoLink;
    private InfoUser usuario;
}
