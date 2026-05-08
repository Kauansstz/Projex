package com.kauan.projex.service;

import org.springframework.stereotype.Service;
import com.kauan.projex.Mapper.CertificatedMapper;
import com.kauan.projex.dto.CertificatedRequest;
import com.kauan.projex.exceptions.DuplicateException;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.CreatedCertificateRepository;


@Service
public class CreatedCertificateService {

    private final CreatedCertificateRepository repository;
    private final CertificatedMapper mapper;

    public CreatedCertificateService(CreatedCertificateRepository repository, CertificatedMapper mapper ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Certificated infoCertificate(CertificatedRequest dto, InfoUser dono) {
        Certificated certificado = new Certificated();
        validarDuplicidade(dto.getTitulo());
        mapper.updateEntityFromDto(dto, certificado);

        if (dto.getAnexo() != null || !dto.getAnexo().isEmpty()) {
            String nomeDoArquivo = dto.getAnexo().getOriginalFilename();
            certificado.setAnexo(nomeDoArquivo);
        }
        certificado.setDono(dono);
        return  repository.save(certificado);

    }


    public Certificated buscarPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new WorkFlowException("Certificado não encontrado"));
    }

    private void validarDuplicidade(String titulo) {
        if (repository.existsByTitulo(titulo)) {
            throw new DuplicateException("Já existe um projeto com este título: ", titulo);
        }
    }

}