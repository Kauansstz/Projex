package com.kauan.projex.service;

import com.kauan.projex.dto.CertificatedRequest;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.repository.CreatedCertificateRepository;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EditCertificateService {

    private final CreatedCertificateRepository certEdit;

    public EditCertificateService(CreatedCertificateRepository certEdit) {
        this.certEdit = certEdit;
    }

    public void infoCertificateEdit(CertificatedRequest request) {

        validarCampos(request);

        Certificated entidade = certEdit.findById(request.getId())
                .orElseThrow(() -> new WorkFlowException("Certificado não encontrado"));

        entidade.setTitulo(request.getTitulo());
        entidade.setDescricao(request.getDescricao());
        entidade.setInstituicao(request.getInstituicao());
        entidade.setTypeCertificate(request.getTypeCertificate());
        entidade.setStatus(request.getStatus());
        entidade.setDataConclusao(request.getDataConclusao());
        entidade.setCategory(request.getCategory());
        entidade.setIsPublish(request.getIsPublish());
        entidade.setDono(request.getDono());

        // 🔥 Tratamento do anexo
        if (request.getAnexo() != null && !request.getAnexo().isEmpty()) {

            // Remove arquivo antigo se existir
            if (entidade.getAnexo() != null) {
                deletarArquivo(entidade.getAnexo());
            }

            String nomeArquivo = salvarArquivo(request.getAnexo());
            entidade.setAnexo(nomeArquivo);

        } else {
            // mantém arquivo antigo
            entidade.setAnexo(request.getAnexoNome());
        }

        certEdit.save(entidade);
    }

    private String salvarArquivo(MultipartFile file) {

        try {
            String uploadDir = "uploads/";
            Path pastaUploads = Paths.get(uploadDir);

            if (!Files.exists(pastaUploads)) {
                Files.createDirectories(pastaUploads);
            }

            String nomeArquivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path caminho = pastaUploads.resolve(nomeArquivo);

            Files.copy(file.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);

            return nomeArquivo;

        } catch (IOException e) {
            throw new WorkFlowException("Erro ao salvar arquivo");
        }
    }

    private void deletarArquivo(String nomeArquivo) {
        try {
            Path caminho = Paths.get("uploads/").resolve(nomeArquivo);
            Files.deleteIfExists(caminho);
        } catch (IOException e) {
            throw new WorkFlowException("Erro ao deletar arquivo antigo");
        }
    }

    private void validarCampos(CertificatedRequest certificado) {

        if (!StringUtils.hasText(certificado.getTitulo())) {
            throw new WorkFlowException("O campo 'Titulo' deve ser preenchido");
        }

        if (!StringUtils.hasText(certificado.getDescricao())) {
            throw new WorkFlowException("O campo 'Descrição' deve ser preenchido");
        }

        if (certificado.getDataConclusao() == null) {
            throw new WorkFlowException("O campo 'Data de Conclusão' deve ser preenchido");
        }

        if (!StringUtils.hasText(certificado.getInstituicao())) {
            throw new WorkFlowException("O campo 'Instituição' deve ser preenchido");
        }

        if (!StringUtils.hasText(certificado.getTypeCertificate())) {
            throw new WorkFlowException("O campo 'Tipo de Certificado' deve ser preenchido");
        }

        if (certificado.getDono() == null) {
            throw new WorkFlowException("Erro ao encontrar o dono");
        }

        if (certificado.getStatus() == null) {
            throw new WorkFlowException("O campo 'Status' deve ser preenchido");
        }
    }

    public CertificatedRequest buscarPorId(Long id) {

        Certificated entidade = certEdit.findById(id)
                .orElseThrow(() -> new WorkFlowException("Certificado não encontrado"));

        CertificatedRequest dto = new CertificatedRequest();

        dto.setId(entidade.getId());
        dto.setTitulo(entidade.getTitulo());
        dto.setDescricao(entidade.getDescricao());
        dto.setInstituicao(entidade.getInstituicao());
        dto.setDataConclusao(entidade.getDataConclusao());
        dto.setTypeCertificate(entidade.getTypeCertificate());
        dto.setStatus(entidade.getStatus());
        dto.setCategory(entidade.getCategory());
        dto.setIsPublish(entidade.getIsPublish());
        dto.setDono(entidade.getDono());
        dto.setAnexoNome(entidade.getAnexo());

        return dto;
    }

    public Certificated salvar(Certificated certificado) {
        return certEdit.save(certificado);
    }
}
