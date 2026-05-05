package com.kauan.projex.service;

import com.kauan.projex.dto.EditProjetoDto;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoUser;
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

    public InfoProject infoCardEdit(Long id,EditProjetoDto dto, InfoUser dono){
        InfoProject project = buscarPorId(id);
        validarCampos(id, dto, dono, project);
        return salvar(project);
    }
    private void validarCampos(Long id, EditProjetoDto dto, InfoUser dono, InfoProject project){
        System.out.println(dto + " E o projeto: " + project);
        try{
            if (dto.getTitulo() != null || dto.getTitulo().isBlank()) {
                throw new WorkFlowException( "O campo 'Titulo' deve ser preenchido");
            }
            if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
                throw new WorkFlowException( "O campo 'Descrição' deve ser preenchido");
            }
            if (dto.getDataConclusao() == null) {
                throw new WorkFlowException("A data de conclusão é obrigatória.");

            }
            if (!StringUtils.hasText(dto.getTecnologiasText())) {
                throw new WorkFlowException("Pelo menos uma tecnologia é obrigatória.");

            }
            
            if (dto.getStatus() != null) {
                String statusStr = dto.getStatus().toString();
                project.setStatus(InfoProject.Status.valueOf(statusStr));
            }
            if (project.getDono() == null) {
                throw new WorkFlowException("O nome do responsável é obrigatório.");
            }
            project.setTitulo(dto.getTitulo());
            project.setDescricao(dto.getDescricao());
            project.setDataConclusao(dto.getDataConclusao());
            project.setDono(dono);
            project.setTecnologiasText(dto.getTecnologiasText());
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
