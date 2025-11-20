package pt.iade.moodly.server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estado_invite")
public class EstadoInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "est_inv_id")
    private Long id;

    @Column(name = "est_inv_nome", nullable = false, length = 60)
    private String nome; // pendente, aceite, recusado

    public EstadoInvite() {}

    public EstadoInvite(String nome) {
        this.nome = nome;
    }

    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
