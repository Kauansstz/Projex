package com.kauan.projex.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.kauan.projex.dto.EditUserDTO;
import com.kauan.projex.dto.LinkDTO;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.service.CenterUserService;
import com.kauan.projex.service.EditUserService;
import com.kauan.projex.utils.TipoLink;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/panelUser")
public class CenterUser {

    private final CenterUserService centerService;
    private final EditUserService editService;

    public CenterUser(CenterUserService centerService,
                      EditUserService editService) {
        this.centerService = centerService;
        this.editService = editService;
    }

    @GetMapping public String listar(Model model, @RequestParam(required = false) String search)
    {
         List<InfoUser> isAtivo = centerService.buscarPorAtivo(true); 
         if (search != null && !search.isBlank()) 
            { isAtivo = centerService.buscarPorNome(search); 
                if (isAtivo.isEmpty()) 
                    { model.addAttribute("mensagemErro", "Nenhum resultado com: " + search); } }
                else{ 
                    isAtivo = centerService.ListarAllUsers(); 
                } 
                model.addAttribute("usuario", isAtivo); 
                model.addAttribute("search",search); 
                model.addAttribute("pageTitle", "Painel do Usuário"); 
                return "pages/panelUser"; }

    private EditUserDTO converterParaDTO(InfoUser user) {

        EditUserDTO dto = new EditUserDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setNameUser(user.getNameUser());
        dto.setEmail(user.getEmail());
        dto.setTelefone(user.getTelefone());
        dto.setCargo(user.getCargo());
        dto.setEmpresa(user.getEmpresa());
        dto.setDescricao(user.getDescricao());
        dto.setGenero(user.getGenero());
        dto.setDataNasc(user.getDataNasc());

        if (user.getLink() != null) {
            user.getLink().forEach(link -> {
                LinkDTO linkDTO = new LinkDTO();
                linkDTO.setId(link.getId());
                linkDTO.setTipoLink(link.getTipoLink());
                linkDTO.setUrl(link.getUrl());
                dto.getLink().add(linkDTO);
            });
        }

        return dto;
    }


    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){

        InfoUser user = centerService.buscarPorId(id);
        EditUserDTO dto = converterParaDTO(user);

        model.addAttribute("user", dto);
        model.addAttribute("tipoLink", TipoLink.values());

        return "pages/panelEditUser";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id,
                            @ModelAttribute("user") EditUserDTO dto,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        try {
            editService.atualizarComDTO(id, dto);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Alterado o usuário com sucesso!");
        } catch (WorkFlowException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/panelUser";
        }

        return "redirect:/panelUser";
    }
}

