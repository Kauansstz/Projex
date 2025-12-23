package com.kauan.projex.service;

import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoProject;
import org.springframework.util.StringUtils;

public class EditCard {
    
    private void validarCampos(InfoProject project){
        if (!StringUtils.hasText(project.getTitulo())) {
            throw new WorkFlowException( "O campo Titulo deve ser preenchido");
        }
        if (!StringUtils.hasText(project.getDescricao())) {
            throw new WorkFlowException( "O campo Descrição deve ser preenchido");
        }
    }
}
