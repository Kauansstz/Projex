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

import com.kauan.projex.model.Certificated;
import com.kauan.projex.service.CardCertificateService;
import com.kauan.projex.utils.Category;

@Controller
@RequestMapping("/panelCertificados")
public class CertificatedController {

    private final CardCertificateService cardCertificateService;

    public CertificatedController(CardCertificateService cardCertificateService) {
        this.cardCertificateService = cardCertificateService;
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
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Certificated certificado = cardCertificateService.buscarPorId(id);
        model.addAttribute("certificado", certificado);
        model.addAttribute("categorias", Category.values());
        return "pages/panelEditCertificate";
    }

    // EDITAR (POST)
    @PostMapping("/editar")
    public String atualizar(Certificated certificado) {
        cardCertificateService.salvar(certificado);
        return "redirect:/panelEditCertificate";
    }

    // EXCLUIR
    @PostMapping("/{id}/deletar")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            cardCertificateService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Projeto excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir o projeto");
        }
        return "redirect:/panelCertificados";
    }
}

