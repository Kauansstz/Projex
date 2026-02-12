package com.kauan.projex.service;

import org.springframework.stereotype.Service;

import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.CenterUserRepository;

@Service
public class EditUserService {

    public final CenterUserRepository repository;

    public EditUserService(CenterUserRepository repository){
        this.repository = repository;
    }

    public InfoUser confirmInfo(InfoUser info){
        validarCampos(info);
            return salvar(info);
    }
    
    public void validarCampos(InfoUser info){
        if (info.getName() == null || info.getDataNasc() == null || info.getEmail() == null || info.getNameUser() == null) {
            throw new WorkFlowException("Os campos devem ser preenchidos");
        }
    }

    public void validarCamposDuplicadosNameUser(String nameUser){
        if (repository.existsByNameUserContainingIgnoreCase(nameUser)) {
            throw new WorkFlowException("O nome de usuário já está sendo usado");
        }
    }

    public InfoUser buscarPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new WorkFlowException("Usuário não encontrado"));
    }

    public InfoUser salvar(InfoUser info){
        return repository.save(info);
    }
    
}
