package com.kauan.projex.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.kauan.projex.utils.Genero;


public class EditUserDTO {
    private Long id;

    private String name;
    private String nameUser;
    private String email;
    private MultipartFile  fotoPerfil;
    private String telefone;
    private String cargo;
    private String empresa;
    private String descricao;
    private Genero genero;
    private LocalDate dataNasc;
    private List<LinkDTO> link = new ArrayList<>();

    public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getNameUser() {
    return nameUser;
}

public void setNameUser(String nameUser) {
    this.nameUser = nameUser;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public MultipartFile getFotoPerfil() {
    return fotoPerfil;
}

public void setFotoPerfil(MultipartFile fotoPerfil) {
    this.fotoPerfil = fotoPerfil;
}

public String getTelefone() {
    return telefone;
}

public void setTelefone(String telefone) {
    this.telefone = telefone;
}

public String getCargo() {
    return cargo;
}

public void setCargo(String cargo) {
    this.cargo = cargo;
}

public String getEmpresa() {
    return empresa;
}

public void setEmpresa(String empresa) {
    this.empresa = empresa;
}

public String getDescricao() {
    return descricao;
}

public void setDescricao(String descricao) {
    this.descricao = descricao;
}

public Genero getGenero() {
    return genero;
}

public void setGenero(Genero genero) {
    this.genero = genero;
}

public LocalDate getDataNasc() {
    return dataNasc;
}

public void setDataNasc(LocalDate dataNasc) {
    this.dataNasc = dataNasc;
}

public List<LinkDTO> getLink() {
    return link;
}

public void setLink(List<LinkDTO> link) {
    this.link = link;
}

}
