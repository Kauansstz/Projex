package com.kauan.projex.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;
import com.kauan.projex.utils.Category;
import com.kauan.projex.utils.Status;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

public class CertificatedRequest {
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "O título é obrigatório.")    
    private String titulo;

    @Column(nullable = false)
    @NotBlank(message = "A Instituição é obrigatória.")
    private String instituicao;

    @Column(nullable = false)
    @NotBlank(message = "O tipo de certificado é obrigatório.")
    private String typeCertificate;

    @Column(nullable = false)
    @NotBlank(message = "A descrição é obrigatória.")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status é obrigatório.")
    private Status status;

    private Boolean isPublish;

    @NotNull(message = "O arquivo de anexo é obrigatório.")
    private MultipartFile anexo;

    private String anexoNome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "A categoria é obrigatória.")
    private Category category;

    @NotNull(message = "O ID do dono é obrigatório")
    private Long donoId; 

    @Column(nullable = false)
    @NotNull(message = "A data de conclusão é obrigatória.")
    private LocalDate dataConclusao;
    private LocalDateTime update = LocalDateTime.now();
    private LocalDateTime criadoEm;

    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getDonoId() {
        return donoId;
    }

    public void setDonoId(Long donoId) {
        this.donoId = donoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }
    public String getTypeCertificate() {
        return typeCertificate;
    }

    public void setTypeCertificate(String typeCertificate) {
        this.typeCertificate = typeCertificate;
    }
    public Boolean getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Boolean isPublish) {
        this.isPublish = isPublish;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getAnexoNome() {
        return anexoNome;
    }

    public void setAnexoNome(String anexoNome) {
        this.anexoNome = anexoNome;
    }

    public MultipartFile  getAnexo() {
        return anexo;
    }

    public void setAnexo(MultipartFile  anexo) {
        this.anexo = anexo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public LocalDateTime getUpdate() {
        return update;
    }

    public void setUpdate(LocalDateTime update) {
        this.update = update;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
