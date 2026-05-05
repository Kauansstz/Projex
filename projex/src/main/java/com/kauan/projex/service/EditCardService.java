package com.kauan.projex.service;

import com.kauan.projex.dto.EditProjetoDto;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.CardRepository;
import com.kauan.projex.repository.CreatedCardRepository;
import org.springframework.stereotype.Service;


@Service
public class EditCardService {
    private final CreatedCardRepository cardEdit;
    private final CardRepository cardRepository;
    public  EditCardService(CreatedCardRepository cardEdit, CardRepository cardRepository){
        this.cardEdit = cardEdit;
        this.cardRepository = cardRepository;
    }

    public InfoProject infoCardEdit(Long id,EditProjetoDto dto, InfoUser dono){
        InfoProject project = buscarPorId(id);
        validarCampos(id, dto, dono, project);
        return salvar(project);
    }
    private void validarCampos(Long id, EditProjetoDto dto, InfoUser dono, InfoProject project){
        try{
            if (dto.getTitulo() != null) {
                if (dto.getTitulo().isBlank()) {
                    throw new WorkFlowException( "O campo 'Titulo' deve ser preenchido");
                }
                project.setTitulo(dto.getTitulo());
            }
            if (dto.getDescricao() != null) {
                if (dto.getDescricao().isBlank()) {
                    throw new WorkFlowException( "O campo 'Descrição' deve ser preenchido");
                }
                project.setDescricao(dto.getDescricao());
            }
            if (dto.getDataConclusao() != null) {
                project.setDataConclusao(dto.getDataConclusao());
            }
            if (dto.getTecnologiasText() != null) {
                project.setTecnologiasText(dto.getTecnologiasText());
            }
            
            if (dto.getStatus() != null) {
                String statusStr = dto.getStatus().toString();
                project.setStatus(InfoProject.Status.valueOf(statusStr));
            }
            project.setDono(dono);
        } catch(Exception e){
            throw new WorkFlowException("Houve um erro inexperado. " + e);
        }

    }
    public InfoProject buscarPorId(Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new WorkFlowException("Projeto não encontrado"));
    }
    public InfoProject salvar(InfoProject project){
        return cardEdit.save(project);
    }
}
