package pt.iade.moodly.server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Estado")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estado_id")
    private Long id;

    @Column(name = "descricao", nullable = false)
    private String descricao; // 'pendente','aceite','recusado','bloqueado','espera'

    public Estado() {}

    public Estado(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
