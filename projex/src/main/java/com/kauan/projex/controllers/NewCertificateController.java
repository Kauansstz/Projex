package com.kauan.projex.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.kauan.projex.dto.CertificatedRequest;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.model.InfoProject;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.TecnologiaRepository;
import com.kauan.projex.service.CreatedCardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class NewCertificateController {
    
    private final TecnologiaRepository tecnologiaRepository;
    private final CreatedCardService repositoryService;


    public NewCertificateController(TecnologiaRepository tecnologiaRepository, CreatedCardService repositoryService) {
        this.tecnologiaRepository = tecnologiaRepository;
        this.repositoryService = repositoryService;

    }


    @PostMapping("/certificate")
    public String salvarCertificado(@ModelAttribute("certificate") CertificatedRequest dto,
                                    HttpServletRequest request,
                                    RedirectAttributes redirectAttributes) {

        try {
            HttpSession session = request.getSession(false);

            if (session == null) {
                throw new WorkFlowException("Sessão expirada.");
            }

            InfoUser dono = (InfoUser) session.getAttribute("usuarioLogado");

            if (dono == null) {
                throw new WorkFlowException("Usuário não autenticado.");
            }

            // DTO → ENTITY
            Certificated certificado = new Certificated();
            certificado.setTitulo(dto.getTitulo());
            certificado.setDescricao(dto.getDescricao());
            certificado.setAnexo(dto.getAnexo());
            certificado.setCategory(dto.getCategory());
            certificado.setDono(dono);

            // createdCardService.salvar(certificado);

            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso", "Certificado cadastrado com sucesso!");

            return "redirect:/panelProjetos";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "mensagemErro", e.getMessage());
            return "redirect:/certificateCreate";
        }
    }

    @PostMapping("/projetos/createdCertificate")
    public String cadastrarCard(@ModelAttribute InfoProject project,
                                HttpServletRequest request, 
                                Model model, 
                                RedirectAttributes redirectAttributes){
        try{
            HttpSession session = request.getSession(false);
            System.out.println("Card Session ID: " + (session != null ? session.getId(): "Sem sessão"));
            String tecnologiasText = project.getTecnologiasText();  
            if (tecnologiasText != null) {
                String texto = project.getTecnologiasText();
                System.out.println("DEBUG FRONT: " + texto);
                List<String> listaTecnologias = Arrays.stream(tecnologiasText.split(";")).map(String::trim).filter(s -> !s.isEmpty()).distinct()
                .toList();
                project.setTecnologiasText(String.join(";", listaTecnologias));
            }
            InfoUser dono = (InfoUser) request.getSession().getAttribute("usuarioLogado");
            if (dono == null) {
                System.out.println("Usuário não autenticado: " + (dono != null ? dono.getEmail() : "null"));
                throw new WorkFlowException("Usuário não autenticado.");
            }
            System.out.println("Usuário autenticado: " + dono.getEmail());

            project.setDono(dono);
            repositoryService.infoCard(project);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Card cadastrado com sucesso!");
            return "redirect:/panelProjetos";

        } catch(Exception e){
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/createdProject";
        }
    }
    @GetMapping("/certificateCreate")
    public String exibirFormulario(Model model){
        model.addAttribute("certificate", new CertificatedRequest());
        model.addAttribute("tecnologias", tecnologiaRepository.findAll());
        model.addAttribute("pageTitle", "Cadastrar Certificado");
        return "pages/newCertificate";
    }
}
