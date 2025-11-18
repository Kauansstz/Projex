package com.kauan.projex.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico",indexes= {
            @Index(name="idx_historico_timestamp", columnList = "created_at"),
            @Index(name = "idx_historico_usuario", columnList = "usuario")})
public class Historico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // quem realizou a ação (pode ser username, id ou sistema)
    @Column(nullable = false, length = 100)
    private String usuario;
    
    // tipo de ação (CREATE, UPDATE, DELETE, LOGIN, LOGOUT, etc.)
    @Column(nullable = false, length = 50)
    private String acao;

    // tipo da entidade afetada
    @Column(length = 100)
    private String entidade;

    // id da entidade afetada
    @Column(name = "entidade_id", length = 100)
    private String entidadeId;

    @Column(columnDefinition = "TEXT")
    private String detalhes;

    @Column(length = 50)
    private String ip;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updateAt", nullable = false)
    private LocalDateTime updateAt;

    public Historico(){}

    public Historico(String usuario, String acao, String entidade, String entidadeId, String detalhes, String ip){
        this.usuario = usuario;
        this.acao = acao;
        this.entidade = entidade;
        this.entidadeId = entidadeId;
        this.detalhes = detalhes;
        this.ip = ip;
        this.updateAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    public String getUsuario(){
        return usuario;
    }
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    public String getAcao(){
        return acao;
    }
    public void setAcao(String acao){
        this.acao = acao;
    }
    public LocalDateTime getUpdateAt(){
        return updateAt;
    }
    public void setUpdateAt(String acao){
        this.updateAt = LocalDateTime.now();
    }
    public String getEntidade(){
        return entidade;
    }
    public void setEntidade(String entidade){
        this.entidade = entidade;
    }
    public String getDetalhes(){
        return detalhes;
    }
    public void setDetalhes(String detalhes){
        this.detalhes = detalhes;
    }

    public String getEntidadeId(){
        return entidadeId;
    }
    public void setEntidadeId(String entidadeId){
        this.entidadeId = entidadeId;
    }
    public String getIp(){
        return ip;
    }
    public void setIp(String ip){
        this.ip = ip;
    }

    public LocalDateTime getCreatedAt(){ return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

}
