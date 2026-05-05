package com.kauan.projex.api.post;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.service.CreatedCardService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/create")
public class CreateProjeto {
    private final CreatedCardService service;
    public CreateProjeto(CreatedCardService service ){
        this.service = service;
    }
    @PostMapping("/projeto")
    public ResponseEntity<?> criarProjeto(Model model, @RequestBody Map<String, Object> dados, HttpServletRequest request, RedirectAttributes redirectAttributes){

        InfoUser dono = (InfoUser) request.getSession().getAttribute("usuarioLogado");
        if (dono == null) {
            throw new WorkFlowException("Usuário não autenticado.");
        }
        try{    
            InfoProject card = new InfoProject();
            String statusStr = String.valueOf(dados.getOrDefault("status", "ANDAMANTO"));
            Object isPublishObj = dados.get("isPublish");
            boolean isPublish = isPublishObj instanceof Boolean ? (Boolean) isPublishObj : false;
            if (dados.get("dataConclusao") != null && !dados.get("dataConclusao").toString().isEmpty()) {
                LocalDate data = LocalDate.parse(dados.get("dataConclusao").toString());
                card.setDataConclusao(data);
            }
            card.setTitulo(dados.getOrDefault("titulo", "").toString());
            card.setDescricao(dados.getOrDefault("descricao", "").toString());
            card.setTecnologiasText(dados.getOrDefault("tecnologiasText", "").toString());
            card.setIsPublish(isPublish);
            card.setDono(dono);
            try {
            card.setStatus(InfoProject.Status.valueOf(statusStr.toUpperCase()));
            } catch (Exception e) {
                card.setStatus(InfoProject.Status.EM_ANDAMENTO);
            }
            InfoProject salvar = service.infoCard(card);
            return ResponseEntity.ok(salvar);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage()); 
        } 
        
    }
}
