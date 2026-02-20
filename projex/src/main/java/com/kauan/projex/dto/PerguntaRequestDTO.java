package com.kauan.projex.dto;

import java.util.List;

import com.kauan.projex.utils.Category;
import com.kauan.projex.utils.TipoPergunta;

public class PerguntaRequestDTO {

    private Long categoriaId;

    private String enunciado;

    private Category categoria; 

    private TipoPergunta tipo;

    private Integer pontuacao;

    private Integer ordem;

    private List<RespostaRequestDTO> respostas;

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }

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

    public List<RespostaRequestDTO> getRespostas() { return respostas; }
    public void setRespostas(List<RespostaRequestDTO> respostas) { this.respostas = respostas; }
}
