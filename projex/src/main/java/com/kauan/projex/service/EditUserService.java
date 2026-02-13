package com.kauan.projex.service;


import org.springframework.stereotype.Service;

import com.kauan.projex.dto.EditUserDTO;
import com.kauan.projex.dto.LinkDTO;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.model.LinkUsuario;
import com.kauan.projex.repository.CenterUserRepository;

@Service
public class EditUserService {

    public final CenterUserRepository repository;

    public EditUserService(CenterUserRepository repository){
        this.repository = repository;
    }

    public void atualizarComDTO(Long id, EditUserDTO dto) {

        InfoUser user = repository.findById(id)
                .orElseThrow();

        user.setName(dto.getName());
        user.setNameUser(dto.getNameUser());
        user.setEmail(dto.getEmail());
        user.setTelefone(dto.getTelefone());
        user.setCargo(dto.getCargo());
        user.setEmpresa(dto.getEmpresa());
        user.setDescricao(dto.getDescricao());
        user.setGenero(dto.getGenero());
        user.setDataNasc(dto.getDataNasc());

        user.getLink().clear();

        for (LinkDTO linkDTO : dto.getLink()) {

            if (linkDTO.getTipoLink() != null &&
                linkDTO.getUrl() != null &&
                !linkDTO.getUrl().isBlank()) {

                LinkUsuario link = new LinkUsuario();
                link.setTipoLink(linkDTO.getTipoLink());
                link.setUrl(linkDTO.getUrl());
                link.setUsuario(user);

                user.getLink().add(link);
            }
        }

        repository.save(user);
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
