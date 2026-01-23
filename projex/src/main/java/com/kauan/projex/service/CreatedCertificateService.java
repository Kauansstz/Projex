package com.kauan.projex.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kauan.projex.exceptions.DuplicateException;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.repository.CreatedCertificateRepository;

@Service
public class CreatedCertificateService {

    private final CreatedCertificateRepository repository;

    public CreatedCertificateService(CreatedCertificateRepository repository) {
        this.repository = repository;
    }


    public Certificated infoCertificate(Certificated certificado) {

        validarCamposObrigatoriosFormCertificate(certificado);
        validarDuplicidade(certificado.getTitulo());

        return salvar(certificado);
    }


    private void validarCamposObrigatoriosFormCertificate(Certificated certificado) {

        if (!StringUtils.hasText(certificado.getTitulo())) {
            throw new WorkFlowException("O título é obrigatório.");
        }

        if (!StringUtils.hasText(certificado.getDescricao())) {
            throw new WorkFlowException("A descrição é obrigatória.");
        }
        if (!StringUtils.hasText(certificado.getInstituicao())) {
            throw new WorkFlowException("A Instituição é obrigatória.");
        }
        if (!StringUtils.hasText(certificado.getTypeCertificate())) {
            throw new WorkFlowException("O tipo de certificado é obrigatória.");
        }

        if (certificado.getStatus() == null) {
            throw new WorkFlowException("O status é obrigatório.");
        }

        if (certificado.getDataConclusao() == null) {
            throw new WorkFlowException("A data de conclusão é obrigatória.");
        }

        if (certificado.getCategory() == null) {
            throw new WorkFlowException("O campo 'Categoria' deve ser preenchido");
        }

        if (certificado.getDono() == null) {
            throw new WorkFlowException("O nome do responsável é obrigatório.");
        }
    }


    private void validarDuplicidade(String titulo) {
        if (repository.existsByTitulo(titulo)) {
            throw new DuplicateException("Já existe um projeto com este título: ", titulo);
        }
    }

    public Certificated salvar(Certificated certificado) {
        return repository.save(certificado);
    }

}