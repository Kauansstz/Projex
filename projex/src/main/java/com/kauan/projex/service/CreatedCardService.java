package com.kauan.projex.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kauan.projex.exceptions.DuplicateException;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.repository.CreatedCardRepository;

@Service
public class CreatedCardService {

    private final CreatedCardRepository repository;

    public CreatedCardService(CreatedCardRepository repository) {
        this.repository = repository;
    }


    public InfoProject infoCard(InfoProject project) {

        validarCamposObrigatorios(project);
        validarDuplicidade(project.getTitulo());

        return salvar(project);
    }


    private void validarCamposObrigatorios(InfoProject project) {

        if (!StringUtils.hasText(project.getTitulo())) {
            throw new WorkFlowException("O título é obrigatório.");
        }

        if (!StringUtils.hasText(project.getDescricao())) {
            throw new WorkFlowException("A descrição é obrigatória.");
        }

        if (project.getStatus() == null) {
            throw new WorkFlowException("O status é obrigatório.");
        }

        if (project.getDataConclusao() == null) {
            throw new WorkFlowException("A data de conclusão é obrigatória.");
        }

        if (!StringUtils.hasText(project.getTecnologiasText())) {
            System.out.println("Debug TecnologiaText: " + project.getTecnologiasText());
            System.out.println("Debug Tecnologia: " + project.getTecnologias());
            throw new WorkFlowException("Pelo menos uma tecnologia é obrigatória.");
        }

        if (project.getDono() == null) {
            throw new WorkFlowException("O nome do responsável é obrigatório.");
        }
    }


    private void validarDuplicidade(String titulo) {
        if (repository.existsByTitulo(titulo)) {
            throw new DuplicateException("Já existe um projeto com este título: ", titulo);
        }
    }

    public InfoProject salvar(InfoProject project) {
        return repository.save(project);
    }

}