package com.kauan.projex.api.patch;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kauan.projex.dto.EditProjetoDto;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.service.EditCardService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/projeto")
public class EditProjeto {
    private final EditCardService service;

    public EditProjeto(EditCardService service){
        this.service = service;
    }

    @PatchMapping("/editProjeto")
    public ResponseEntity<?> editarProjeto(@RequestParam(required = false) Long id, @RequestBody EditProjetoDto dto,HttpServletRequest request){
       InfoUser dono = (InfoUser) request.getSession().getAttribute("usuarioLogado");

        InfoProject salvo = service.infoCardEdit(id, dto, dono);

        return ResponseEntity.ok(salvo);
    }
}
