package com.kauan.projex.service;

import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.repository.CardRepository;
import com.kauan.projex.repository.CreatedCardRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EditCardService {
    private final CreatedCardRepository cardEdit;
    private final CardRepository cardRepository;
    public  EditCardService(CreatedCardRepository cardEdit, CardRepository cardRepository){
        this.cardEdit = cardEdit;
        this.cardRepository = cardRepository;
    }

    public InfoProject infoCardEdit(InfoProject project){
        validarCampos(project);
        return salvar(project);
    }
    private void validarCampos(InfoProject project){
        if (!StringUtils.hasText(project.getTitulo())) {
            throw new WorkFlowException( "O campo 'Titulo' deve ser preenchido");
        }
        if (!StringUtils.hasText(project.getDescricao())) {
            throw new WorkFlowException( "O campo 'Descrição' deve ser preenchido");
        }
        if (project.getDataConclusao() == null) {
            throw new WorkFlowException( "O campo 'Data de Conclusão' deve ser preenchido");
        }
        if (!StringUtils.hasText(project.getTecnologiasText())) {
            throw new WorkFlowException( "O campo 'Tecnologias' deve ser preenchido");
        }
        
    }
    public InfoProject buscarPorId(Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new WorkFlowException("Projeto não encontrado"));
    }
    public InfoProject salvar(InfoProject project){
        return cardEdit.save(project);
    }
}
