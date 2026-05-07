package com.kauan.projex.api.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kauan.projex.dto.CertificatedRequest;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.service.CreatedCertificateService;

@RestController
@RequestMapping("api/v1")
public class CreateCertificados {
    private final CreatedCertificateService service;

    public CreateCertificados(CreatedCertificateService service){
        this.service = service;
    }

    public ResponseEntity<?> criarProjeto(Long id, @RequestBody CertificatedRequest dto){
        Certificated salvo =  service.infoCertificate(id, dto);
        return ResponseEntity.ok(salvo);
    }
}
