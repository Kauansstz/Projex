package com.kauan.projex.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.kauan.projex.utils.NivelDificuldade;



@Entity
@Table(name = "quest")
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private NivelDificuldade nivelDificuldade;

    private Integer tempoLimiteSegundos;

    private Integer pontuacaoTotal;

    private Integer tentativasMaximas;

    private Boolean ativo;

    private Boolean randomizarPerguntas;

    private Boolean randomizarRespostas;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;

    private LocalDateTime publicadoEm;

    @OneToMany(mappedBy = "quest")
    private List<Pergunta> perguntas;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public NivelDificuldade getNivelDificuldade() { return nivelDificuldade; }
    public void setNivelDificuldade(NivelDificuldade nivelDificuldade) { this.nivelDificuldade = nivelDificuldade; }

    public Integer getTempoLimiteSegundos() { return tempoLimiteSegundos; }
    public void setTempoLimiteSegundos(Integer tempoLimiteSegundos) { this.tempoLimiteSegundos = tempoLimiteSegundos; }

    public Integer getPontuacaoTotal() { return pontuacaoTotal; }
    public void setPontuacaoTotal(Integer pontuacaoTotal) { this.pontuacaoTotal = pontuacaoTotal; }

    public Integer getTentativasMaximas() { return tentativasMaximas; }
    public void setTentativasMaximas(Integer tentativasMaximas) { this.tentativasMaximas = tentativasMaximas; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Boolean getRandomizarPerguntas() { return randomizarPerguntas; }
    public void setRandomizarPerguntas(Boolean randomizarPerguntas) { this.randomizarPerguntas = randomizarPerguntas; }

    public Boolean getRandomizarRespostas() { return randomizarRespostas; }
    public void setRandomizarRespostas(Boolean randomizarRespostas) { this.randomizarRespostas = randomizarRespostas; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }

    public LocalDateTime getPublicadoEm() { return publicadoEm; }
    public void setPublicadoEm(LocalDateTime publicadoEm) { this.publicadoEm = publicadoEm; }

    public List<Pergunta> getPerguntas() { return perguntas; }
    public void setPerguntas(List<Pergunta> perguntas) { this.perguntas = perguntas; }
}
