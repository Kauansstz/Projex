package com.kauan.projex.api.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kauan.projex.repository.ProjectRepository;

import io.micrometer.common.lang.NonNull;


@RestController
@RequestMapping("/api/v1/delete")
public class DeleteProjeto {
    
    @Autowired
    public ProjectRepository pRepository;


    @DeleteMapping("/projeto/{id}")
    public ResponseEntity<?> deleteProjeto(@PathVariable("id") @NonNull Long id ){
        if (!pRepository.existsById(id)){
            return ResponseEntity.status(404).body("Projeto não encontrado com o ID: " + id);
        }
        pRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
