package com.kauan.projex.model;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "historico",
        indexes= {
            @Index(name="idx_historico_timestamp", columnList = "created_at"),
            @Index(name = "idx_historico_usuario", columnList = "usuario")
        }
)
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
    private OffsetDateTime createdAt;

    public Historico(){}

    public Historico(String usuario, String acao, String entidade, String entidadeId, String detalhes, String ip){
        this.usuario = usuario;
        this.acao = acao;
        this.entidade = entidade;
        this.entidadeId = entidadeId;
        this.detalhes = detalhes;
        this.ip = ip;
        this.createdAt = OffsetDateTime.now();
    }

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
