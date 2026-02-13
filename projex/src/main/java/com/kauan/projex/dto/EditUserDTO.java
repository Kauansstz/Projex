package com.kauan.projex.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kauan.projex.utils.Genero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
