package com.kauan.projex.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kauan.projex.exceptions.UnauthorizedException;
import com.kauan.projex.model.Historico;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.repository.ProjectRepository;

@Service
public class DashBoardService {
    @Autowired
    private final ProjectRepository projectRepository;
    
    public DashBoardService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<InfoProject> buscarUltimosProjetos(int limti){
        return projectRepository.findTop3ByOrderByAtualizadoEmDesc();
    }
    public String mensagemProjetos(boolean tem) {
    if (!tem) {
        return "<span class='bg-white rounded-lg shadow p-4 hover:bg-gray-50 transition'>Nenhum projeto selecionado</span>";
    }
    return ""; // vazio para quando tem projetos
}

    public String validarCamposDashBoard(InfoUser usuarioLogado, InfoProject project, Historico history, String tempo) {

        if (usuarioLogado == null) {
            throw new UnauthorizedException("Nenhum usuário encontrado");
        }

        StringBuilder body = new StringBuilder();
        if (project == null) {
            body.append("<span class='block font-semibold text-gray-800'>Nenhum projeto selecionado</span>");
            return body.toString();
        }
        // ------ STATUS DO PROJETO ------
        switch (project.getStatus()) {
            case EM_ANDAMENTO -> 
                body.append("<span class='block text-sm text-yellow-500'>Pendente</span>");
            case CONCLUIDO -> 
                body.append("<span class='block text-sm text-green-600'>Concluído</span>");
            case CANCELADO -> 
                body.append("<span class='block text-sm text-red-600'>Cancelado</span>");
            default ->
                body.append("<span class='block text-sm text-gray-500'>-</span>");
        }

        return body.toString().strip();
    }
}
