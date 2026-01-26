package com.kauan.projex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.kauan.projex.dto.CertificatedRequest;
import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.service.CreatedCertificateService;
import com.kauan.projex.utils.Category;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class NewCertificateController {
    
    private final CreatedCertificateService repositoryService;


    public NewCertificateController(CreatedCertificateService repositoryService) {
        this.repositoryService = repositoryService;

    }


    @PostMapping("/certificate")
public String salvarCertificado(
        @ModelAttribute("certificate") CertificatedRequest dto,
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

        MultipartFile arquivo = dto.getAnexo();
        if (arquivo == null || arquivo.isEmpty()) {
            throw new WorkFlowException("Nenhum arquivo foi enviado.");
        }

        Certificated certificado = new Certificated();
        certificado.setTitulo(dto.getTitulo());
        certificado.setDescricao(dto.getDescricao());
        certificado.setCategory(dto.getCategory());
        certificado.setDono(dono);

        // ✅ agora sim
        certificado.setAnexo(arquivo.getOriginalFilename());

        redirectAttributes.addFlashAttribute(
                "mensagemSucesso", "Certificado cadastrado com sucesso!");

        return "redirect:/panelProjetos";

    } catch (Exception e) {
        redirectAttributes.addFlashAttribute(
                "mensagemErro", e.getMessage());
        return "redirect:/certificateCreate";
    }
}

    @PostMapping("/certificate/createdCertificate")
        public String cadastrarCard(
                @ModelAttribute CertificatedRequest dto,
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

                MultipartFile arquivo = dto.getAnexo();
                if (arquivo == null || arquivo.isEmpty()) {
                    throw new WorkFlowException("Nenhum arquivo foi enviado.");
                }

                Certificated certificado = new Certificated();
                certificado.setTitulo(dto.getTitulo());
                certificado.setDescricao(dto.getDescricao());
                certificado.setCategory(dto.getCategory());
                certificado.setTypeCertificate(dto.getTypeCertificate());
                certificado.setInstituicao(dto.getInstituicao());
                certificado.setStatus(dto.getStatus());
                certificado.setDataConclusao(dto.getDataConclusao());
                certificado.setDono(dono);

                // salva apenas o nome (ou caminho)
                certificado.setAnexo(arquivo.getOriginalFilename());

                repositoryService.infoCertificate(certificado);

                redirectAttributes.addFlashAttribute(
                        "mensagemSucesso", "Certificado cadastrado com sucesso!");

                return "redirect:/panelCertificados";

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute(
                        "mensagemErro", e.getMessage());
                return "redirect:/certificateCreate";
            }
        }

    @GetMapping("/certificateCreate")
    public String exibirFormulario(Model model){
        model.addAttribute("certificate", new CertificatedRequest());
        model.addAttribute("categorias", Category.values());

        model.addAttribute("pageTitle", "Cadastrar Certificado");
        return "pages/newCertificate";
    }
}
