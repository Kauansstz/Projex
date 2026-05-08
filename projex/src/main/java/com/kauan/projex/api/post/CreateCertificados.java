package com.kauan.projex.api.post;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kauan.projex.dto.CertificatedRequest;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.service.CreatedCertificateService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1")
public class CreateCertificados {
    private final CreatedCertificateService service;

    public CreateCertificados(CreatedCertificateService service){
        this.service = service;
    }

    @PostMapping(value = "/criarProjeto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> criarProjeto( @ModelAttribute CertificatedRequest dto, HttpServletRequest request){
        InfoUser dono = (InfoUser) request.getSession().getAttribute("usuarioLogado");
        Certificated salvo =  service.infoCertificate( dto, dono);
        System.out.println("Arquivo recebido: " + dto.getAnexo().getOriginalFilename());
        return ResponseEntity.ok(salvo);
    }
}
