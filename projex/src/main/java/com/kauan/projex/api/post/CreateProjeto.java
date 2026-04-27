package com.kauan.projex.api.post;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.CardRepository;
import com.kauan.projex.repository.UsuarioRepository;
import com.kauan.projex.service.CreatedCardService;

@RestController
@RequestMapping("api/v1/create")
public class CreateProjeto {
    private final UsuarioRepository repositoryUsuario;
    private final CardRepository repositoryCard;
    private final CreatedCardService service;
    public CreateProjeto(UsuarioRepository repositoryUsuario, CreatedCardService service, CardRepository repositoryCard){
        this.repositoryUsuario = repositoryUsuario;
        this.repositoryCard = repositoryCard;
        this.service = service;
    }
    @PostMapping("/projeto")
    public ResponseEntity<?> criarProjeto(Model model, @RequestBody Map<String, String> dados){
        try{    
            InfoProject card = new InfoProject();
            String status = dados.getOrDefault("status", "").toString();
            Long id = Long.parseLong(dados.getOrDefault("dono", "").toString());
            InfoUser usuario = repositoryUsuario.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            card.setTitulo(dados.getOrDefault("titulo", "").toString());
            card.setDescricao(dados.getOrDefault("descricao", "").toString());
            card.setStatus(InfoProject.Status.valueOf(status.toUpperCase()));
            card.setTecnologiasText(dados.getOrDefault("tecnologiasText", "").toString());
            card.setDono(usuario);

            if (dados.containsKey("dataConclusao")) {
                String dataConclusao = dados.getOrDefault("dataConclusao", "2026-01-01").toString();
                LocalDate dataConclusaoFormat = LocalDate.parse(dataConclusao);
                card.setDataConclusao(dataConclusaoFormat);
            }

            service.infoCard(card);
            InfoProject salvar = repositoryCard.save(card);
            return ResponseEntity.ok(salvar);
        }catch(WorkFlowException e){
            return ResponseEntity.internalServerError().body("Erro ao criar o projeto: " + e.getMessage());
        }
        
    }
}
