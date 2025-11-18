package com.kauan.projex.service;

import org.springframework.stereotype.Service;


import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.Historico;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.model.InforProject;

@Service
public class DashBoardService {
    
    public String  validarCamposDashBoard(InfoUser usuarioLogado, InforProject project, Historico history, String tempo){
       if (usuarioLogado == null) {
        throw new WorkFlowException("Nenhum usuário encontrado");
    }

    StringBuilder body = new StringBuilder();

    if (project == null) {
        return "<span class='block font-semibold text-gray-800'>Nenhum projeto selecionado</span>";
    }
    // ---- HISTÓRICO ----
    if (history == null || history.getUpdateAt() == null) {
        body.append("<span class='block text-sm text-gray-500'>Nenhuma atualização</span>");
    }

    // ---- STATUS DO PROJETO ----
    if (project.getStatus() == InforProject.Status.EM_ANDAMENTO) {
        body.append("<span class='block text-sm text-yellow-500'>Pendente</span>");
    } 
    else if (project.getStatus() == InforProject.Status.CONCLUIDO) {
        body.append("<span class='block text-sm text-green-600'>Concluído</span>");
    } 
    else if (project.getStatus() == InforProject.Status.CANCELADO) {
        body.append("<span class='block text-sm text-red-600'>Cancelado</span>");
    } else{
        body.append("<span class='block text-sm text-gray-500'>-</span>");
    }
    System.out.println("Body do Dashboard: "+body.toString());

    return body.toString().trim();
        
        
    }
}
