package com.kauan.projex.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kauan.projex.dto.EditUserDTO;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.CardCertificateRepository;
import com.kauan.projex.service.CardService;
import com.kauan.projex.service.EditUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

@Controller
@RequestMapping("editUser/")
public class EditUserController {
    private final EditUserService service;
    private  final CardService cardService;
    private  final CardCertificateRepository certficateRepository;
    
    public EditUserController(EditUserService service, CardService cardService, CardCertificateRepository certficateRepository){
        this.service = service;
        this.cardService = cardService;
        this.certficateRepository = certficateRepository;
    }
    
    @PostMapping("/editar/{id}")
    public String editUser(@PathVariable Long id,
                        @ModelAttribute("user") EditUserDTO formUser,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {

        try {
            service.atualizarComDTO(id, formUser);

            redirectAttributes.addFlashAttribute("mensagemSucesso",
                    "Usuário alterado com sucesso!");

            return "redirect:/panelUser";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("mensagemErro",
                    e.getMessage());

            return "redirect:/panelUser";
        }
    }

    @GetMapping("/editar/{id}")
    public String exibirUser(@PathVariable Long id, Model model){
        InfoUser user= service.buscarPorId(id);
        model.addAttribute("user",user);
        model.addAttribute("pageTitle", "Edição do usuário");
        return "pages/panelEditUser";
    }
    @GetMapping("/viewer/{id}")
    public String viewerUser(@PathVariable Long id, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
       
        InfoUser user= service.buscarPorId(id);

        InfoUser usuario =
            (InfoUser) request.getSession().getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }
        if (cardService == null) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Nenhum card encontrado");
        }
        List<InfoProject> ultimosProjetos = cardService.buscarUltimos2Projetos(usuario);
        List<Certificated> certificados = certficateRepository.findTop2ByDonoOrderByIdDesc(usuario);

        model.addAttribute("certificados", certificados);
        model.addAttribute("ultimosProjetos", ultimosProjetos);       
        model.addAttribute("user",user);
        model.addAttribute("pageTitle", "Edição do usuário");
        return "pages/panelViewerUser";
    }

}
