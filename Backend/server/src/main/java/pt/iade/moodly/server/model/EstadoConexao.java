package pt.iade.moodly.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estadoc")
public class EstadoConexao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estadoc_id")
    private Long id;

    @Column(name = "estadoc_descricao", nullable = false, length = 60)
    private String descricao; 

    public EstadoConexao() {}

    public EstadoConexao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {return id;}

    public String getDescricao() {return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
