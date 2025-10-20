package com.kauan.projex.dto;

public class HistoricoDTO {
    private String usuario;
    private String acao;
    private String entidade;
    private String entidadeId;
    private String detalhes;
    private String ip;

    public String getUsuario(){
        return usuario;
    }
    public String setUsuario(String usuario){
        return this.usuario = usuario;
    }
    public String getAcao(){
        return acao;
    }
    public String setAcao(String acao){
        return this.acao = acao;
    }
    public String getEntidade(){
        return entidade;
    }
    public String setEntidade(String entidade){
        return this.entidade = entidade;
    }
    public String getDetalhes(){
        return detalhes;
    }
    public String setDetalhes(String detalhes){
        return this.detalhes = detalhes;
    }
    public String getEntidadeId(){
        return entidadeId;
    }
    public String setEntidadeId(String entidadeId){
        return this.entidadeId = entidadeId;
    }
    public String getIp(){
        return ip;
    }
    public String setIp(String ip){
        return this.ip = ip;
    }

}
