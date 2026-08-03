package com.kauan.projex.api.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kauan.projex.repository.CardCertificateRepository;
import io.micrometer.common.lang.NonNull;


@RestController
@RequestMapping("/api/v1/delete")
public class DeleteCertificado {
    
    @Autowired
    public CardCertificateRepository cRepository;


    @DeleteMapping("/certificado/{id}")
    public ResponseEntity<?> deleteCertificated(@PathVariable("id") @NonNull Long id ){
        if (!cRepository.existsById(id)){
            return ResponseEntity.status(404).body("Certificado não encontrado com o ID: " + id);
        }
        cRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
