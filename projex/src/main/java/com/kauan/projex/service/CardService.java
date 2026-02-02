package com.kauan.projex.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.CardRepository;

@Service
public class CardService {

    private final CardRepository cardRepository;
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<InfoProject> listarTodos() {
        return cardRepository.findAll();
    }

    public List<InfoProject> buscarPorTitulo(String titulo) {
        return cardRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<InfoProject> buscarUltimos3Projetos(InfoUser usuario) {
        return cardRepository.findTop3ByDonoOrderByCriadoEmDesc(usuario);
    }

    public List<InfoProject> buscarPorPublicado(Boolean isPublish){
        return cardRepository.findByIsPublish(isPublish);
    }

    public InfoProject buscarPorId(Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new WorkFlowException("Projeto não encontrado"));
    }

    public InfoProject salvar(InfoProject projeto) {
        return cardRepository.save(projeto);
    }

    public void deletar(Long id) {
        if (!cardRepository.existsById(id)) {
            throw new WorkFlowException("Projeto não encontrado para exclusão");
        }
        cardRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return cardRepository.existsById(id);
    }
}
