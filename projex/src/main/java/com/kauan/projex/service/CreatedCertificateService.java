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

    public Certificated infoCertificate(CertificatedRequest dto, InfoUser dono) {
        Certificated certificado = new Certificated();
        validarDuplicidade(dto.getTitulo());
        mapper.updateEntityFromDto(dto, certificado);

        if (dto.getAnexo() != null || !dto.getAnexo().isEmpty()) {
            String nomeDoArquivo = dto.getAnexo().getOriginalFilename();
            certificado.setAnexo(nomeDoArquivo);
        }
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