package com.kauan.projex.api.put;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.UsuarioRepository;

@RestController
@RequestMapping("api/v1/editUser")
public class EditUserApi {
    private final UsuarioRepository repository;

    public EditUserApi(UsuarioRepository repository){
        this.repository = repository;
    }

    @PutMapping
    public ResponseEntity<?> editUsuario(@RequestParam(required = false) String cpf, @RequestBody java.util.Map<String, Object>  novosDados){
        List<InfoUser> buscar = repository.findByCpf(cpf);
        if (buscar == null || buscar.isEmpty()) {
            return ResponseEntity.status(404).body("Usuário com este CPF não encontrado.");
        }   
        InfoUser usuarioNoBanco = buscar.get(0);

       if (!novosDados.containsKey("area") || novosDados.get("area").toString().isEmpty()) {
        return ResponseEntity.badRequest().body("O campo 'Area' é obrigatório");
        }
        if (!novosDados.containsKey("name") || novosDados.get("name").toString().isEmpty()) {
            return ResponseEntity.badRequest().body("O campo 'Nome' é obrigatório");
        }
        if (!novosDados.containsKey("nameUser")  || novosDados.get("nameUser").toString().isEmpty()) {
            return ResponseEntity.badRequest().body("O campo 'nameUser' é obrigatório");
        }  
        if (!novosDados.containsKey("cpf")|| novosDados.get("cpf").toString().isEmpty()) {
            return ResponseEntity.badRequest().body("O campo 'cpf' é obrigatório");
        }  
        if (!novosDados.containsKey("password") || novosDados.get("password").toString().isEmpty()) {
            return ResponseEntity.badRequest().body("O campo 'Senha' é obrigatório");
        }  

        usuarioNoBanco.setArea(novosDados.get("area").toString());
        usuarioNoBanco.setName(novosDados.get("name").toString());
        usuarioNoBanco.setNameUser(novosDados.get("nameUser").toString());
        usuarioNoBanco.setCpf(novosDados.get("cpf").toString());
        usuarioNoBanco.setPassword(novosDados.get("password").toString());

        InfoUser usuarioAtualizado = repository.save(usuarioNoBanco);

        return ResponseEntity.ok(usuarioAtualizado);

    }
}
