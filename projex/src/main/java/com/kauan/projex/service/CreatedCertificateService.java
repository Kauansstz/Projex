package com.kauan.projex.service;

import org.springframework.stereotype.Service;
import com.kauan.projex.Mapper.CertificatedMapper;
import com.kauan.projex.dto.CertificatedRequest;
import com.kauan.projex.exceptions.DuplicateException;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.CreatedCertificateRepository;
import com.kauan.projex.repository.UsuarioRepository;


@Service
public class CreatedCertificateService {

    private final CreatedCertificateRepository repository;
    private final CertificatedMapper mapper;
    private final UsuarioRepository usuarioRepository;

    public CreatedCertificateService(CreatedCertificateRepository repository, CertificatedMapper mapper, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.usuarioRepository = usuarioRepository;
    }

    public Certificated infoCertificate(Long id, CertificatedRequest dto) {
        Certificated certificado = buscarPorId(id);
        InfoUser dono = usuarioRepository.findById(dto.getDonoId()).orElseThrow(() ->  new WorkFlowException("Usuário não encontrado"));

        validarDuplicidade(dto.getTitulo());
        certificado.setDono(dono);
        mapper.updateEntityFromDto(dto, certificado);
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