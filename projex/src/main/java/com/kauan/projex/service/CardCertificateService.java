package com.kauan.projex.service;


import java.util.List;


import org.springframework.stereotype.Service;

import com.kauan.projex.dto.CertificatedRequest;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.CardCertificateRepository;
import com.kauan.projex.utils.Category;

@Service
public class CardCertificateService {

    private final CardCertificateRepository cardCertificateRepository;

    public CardCertificateService(CardCertificateRepository cardCertificateRepository) {
        this.cardCertificateRepository = cardCertificateRepository;
    }

    public List<Certificated> listarTodos() {
        return cardCertificateRepository.findAll();
    }

    public List<Certificated> buscarPorTitulo(String titulo) {
        return cardCertificateRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Certificated> buscarUltimos3Projetos(InfoUser usuario) {
        return cardCertificateRepository.findTop3ByDonoOrderByCriadoEmDesc(usuario);
    }

    public CertificatedRequest buscarPorId(Long id) {

        Certificated entidade = cardCertificateRepository.findById(id)
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

        return dto;
    }

    public Certificated salvar(Certificated certificado) {
        return cardCertificateRepository.save(certificado);
    }

    public void deletar(Long id) {
        if (!cardCertificateRepository.existsById(id)) {
            throw new WorkFlowException("Projeto não encontrado para exclusão");
        }
        cardCertificateRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return cardCertificateRepository.existsById(id);
    }

    public List<Certificated> buscarPublish(Boolean isPublish){
        return cardCertificateRepository.findByIsPublish(isPublish);
    }

    public List<Certificated> filtrar(String search, Category category, Boolean isPublish){
        return cardCertificateRepository.filtrar(search, category, isPublish);
    }

}
