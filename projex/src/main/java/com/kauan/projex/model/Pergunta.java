package com.kauan.projex.model;

import com.kauan.projex.utils.Category;
import com.kauan.projex.utils.TipoPergunta;

import jakarta.persistence.*;

@Entity
@Table(name = "pergunta")
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category categoria; 

    private String enunciado;

    @Enumerated(EnumType.STRING)
    private TipoPergunta tipo;

    private Integer pontuacao;

    private Integer ordem;

    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "quest_id")
    private Quest quest;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Category getCategoria() { return categoria; }
    public void setCategoria(Category categoria) { this.categoria = categoria; }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }

    public TipoPergunta getTipo() { return tipo; }
    public void setTipo(TipoPergunta tipo) { this.tipo = tipo; }

    public Integer getPontuacao() { return pontuacao; }
    public void setPontuacao(Integer pontuacao) { this.pontuacao = pontuacao; }

    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Quest getQuest() { return quest; }
    public void setQuest(Quest quest) { this.quest = quest; }

}


