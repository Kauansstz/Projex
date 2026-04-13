package com.kauan.projex.api.get;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/v1/user")
public class UserApi {
    private UsuarioRepository repository;

    public UserApi(UsuarioRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<InfoUser> listarUser(@RequestParam(required = false) String cpf){
        if (cpf != null) {
            return repository.findByCpf(cpf); 
        }
     
     return repository.findAll();
    }
}
