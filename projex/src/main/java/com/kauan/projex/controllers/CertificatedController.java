package com.kauan.projex.controllers;



import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kauan.projex.exceptions.WorkFlowException;
import com.kauan.projex.model.Certificated;
import com.kauan.projex.model.InfoUser;
import com.kauan.projex.service.CardCertificateService;
import com.kauan.projex.service.EditCertificateService;
import com.kauan.projex.utils.Category;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/panelCertificados")
public class CertificatedController {

    private final CardCertificateService cardCertificateService;
    private final EditCertificateService editCardService;

    public CertificatedController(CardCertificateService cardCertificateService, EditCertificateService editCardService) {
        this.cardCertificateService = cardCertificateService;
        this.editCardService = editCardService;
    }

    // LISTAR + BUSCAR
    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String search, @RequestParam(required = false) Category category) {

        List<Certificated> certificados = cardCertificateService.filtrar(search, category);

        model.addAttribute("certificados", certificados);
        model.addAttribute("search", search);
        model.addAttribute("category", category);
        model.addAttribute("categorias", Category.values());
        model.addAttribute("pageTitle", "Certificado");
        return "pages/panelCertificados";
    }

    // EDITAR (TELA)
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Certificated certificado = cardCertificateService.buscarPorId(id);
        model.addAttribute("certificado", certificado);
        model.addAttribute("categorias", Category.values());
        return "pages/panelEditCertificate";
    }

    // EDITAR (POST)
    @PostMapping("/{id}/editar")
    public String atualizar(@PathVariable Long id,Certificated certificado,RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {
        try{
            certificado.setId(id);
            InfoUser dono = (InfoUser) request.getSession().getAttribute("usuarioLogado");
            if (dono == null) {
                throw new WorkFlowException("Usuário não autenticado.");
            }
            System.out.println("Usuário autenticado: " + dono.getEmail());
            certificado.setDono(dono);
            editCardService.infoCertificateEdit(certificado);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Certificado atualizado com sucesso!");
        } catch (WorkFlowException e) {
                redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
                return "redirect:/panelCertificados/" + id + "/editar";
            }
    return "redirect:/panelCertificados";
    }

    // EXCLUIR
    @PostMapping("/{id}/deletar")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            cardCertificateService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Certificado excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir o certificado");
        }
        return "redirect:/panelCertificados";
    }
}

