package com.kauan.projex.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kauan.projex.utils.Category;
import com.kauan.projex.utils.TipoPergunta;
import jakarta.persistence.*;

@Entity
@Table(name = "pergunta")
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category categoria; 

    private String enunciado;

    @Enumerated(EnumType.STRING)
    private TipoPergunta tipo;

    private Integer pontuacao;
    private Integer ordem;
    private Boolean ativo;

    // --- NOVOS CAMPOS PARA AS ALTERNATIVAS ---
    @Column(columnDefinition = "TEXT")
    private String alternativaA;

    @Column(columnDefinition = "TEXT")
    private String alternativaB;

    @Column(columnDefinition = "TEXT")
    private String alternativaC;

    @Column(columnDefinition = "TEXT")
    private String alternativaD;

    @Column(name = "nivel", length = 50)
    @JsonProperty("nivel")
    private String nivel;

    @OneToMany(mappedBy = "pergunta", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Resposta> respostas;

    @ManyToOne
    @JoinColumn(name = "quest_id")
    private Quest quest;

    public Pergunta() {}

    // --- GETTERS E SETTERS EXISTENTES ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Category getCategoria() { return categoria; }
    public void setCategoria(Category categoria) { this.categoria = categoria; }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }

    public TipoPergunta getTipo() { return tipo; }
    public void setTipo(TipoPergunta tipo) { this.tipo = tipo; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public List<Resposta> getRespostas() { return respostas; }
    public void setRespostas(List<Resposta> respostas) { this.respostas = respostas; }

    public Integer getPontuacao() { return pontuacao; }
    public void setPontuacao(Integer pontuacao) { this.pontuacao = pontuacao; }

    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Quest getQuest() { return quest; }
    public void setQuest(Quest quest) { this.quest = quest; }

    // --- NOVOS GETTERS E SETTERS ---

    public String getAlternativaA() { return alternativaA; }
    public void setAlternativaA(String alternativaA) { this.alternativaA = alternativaA; }

    public String getAlternativaB() { return alternativaB; }
    public void setAlternativaB(String alternativaB) { this.alternativaB = alternativaB; }

    public String getAlternativaC() { return alternativaC; }
    public void setAlternativaC(String alternativaC) { this.alternativaC = alternativaC; }

    public String getAlternativaD() { return alternativaD; }
    public void setAlternativaD(String alternativaD) { this.alternativaD = alternativaD; }
}