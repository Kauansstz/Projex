package com.kauan.projex.service;

import com.kauan.projex.dto.EditProjetoDto;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.CardRepository;
import com.kauan.projex.repository.CreatedCardRepository;

import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

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
    @Transactional
    private void validarCampos(Long id, EditProjetoDto dto, InfoUser dono, InfoProject project){
        
        if (dono == null || dono.getId() == null) {
            throw new WorkFlowException("Usuário logado não encontrado na sessão.");
        }

        try{
            if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
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
           
            project.setTitulo(dto.getTitulo());
            project.setDescricao(dto.getDescricao());
            project.setDataConclusao(dto.getDataConclusao());
            project.setDono(dono);
            project.setIsPublish(dto.getIsPublish());
            project.setTecnologiasText((limparTecnologias(dto.getTecnologiasText())));
        } catch(WorkFlowException e){
            throw  e;
        }catch(Exception e){
            throw new WorkFlowException("Houve um erro inexperado. " + e);
        }

    }
    private String limparTecnologias(String texto) {
        if (texto == null) return "";
        List<String> lista = Arrays.stream(texto.split(";"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .toList();
        return String.join(";", lista);
    }
    public InfoProject buscarPorId(Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new WorkFlowException("Projeto não encontrado"));
    }
    public InfoProject salvar(InfoProject project){
        return cardEdit.save(project);
    }
}
