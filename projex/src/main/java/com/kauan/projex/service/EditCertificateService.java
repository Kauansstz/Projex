package com.kauan.projex.service;

import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.repository.CreatedCertificateRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EditCertificateService {
    private final CreatedCertificateRepository certEdit;
    public  EditCertificateService(CreatedCertificateRepository certEdit ){
        this.certEdit = certEdit;

    }

    public Certificated infoCertificateEdit(Certificated certificado){
        validarCampos(certificado);
        return salvar(certificado);
    }
    private void validarCampos(Certificated certificado){
        if (!StringUtils.hasText(certificado.getTitulo())) {
            throw new WorkFlowException( "O campo 'Titulo' deve ser preenchido");
        }
        if (!StringUtils.hasText(certificado.getDescricao())) {
            throw new WorkFlowException( "O campo 'Descrição' deve ser preenchido");
        }
        if (certificado.getDataConclusao() == null) {
            throw new WorkFlowException( "O campo 'Data de Conclusão' deve ser preenchido");
        }
        if (!StringUtils.hasText(certificado.getInstituicao())) {
            throw new WorkFlowException( "O campo 'Instituição' deve ser preenchido");
        }
        if (!StringUtils.hasText(certificado.getTypeCertificate())) {
            throw new WorkFlowException( "O campo 'Tipo de Certificado' deve ser preenchido");
        }
        if (certificado.getStatus() == null) {
            throw new WorkFlowException( "O campo 'Status' deve ser preenchido");
        }
        
    }
    public Certificated buscarPorId(Long id) {
        return certEdit.findById(id).orElseThrow(() -> new WorkFlowException("Certificado não encontrado"));
    }

    public Certificated salvar(Certificated certificado){
        return certEdit.save(certificado);
    }
}
