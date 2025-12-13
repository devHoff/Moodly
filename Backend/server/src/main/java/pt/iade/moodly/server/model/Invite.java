package pt.iade.moodly.server.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invite")
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invite_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invite_evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "invite_usuar_id", nullable = false)
    private Usuario convidado;

    @ManyToOne
    @JoinColumn(name = "invite_est_inv_id")
    private EstadoInvite estado;

    @Column(name = "invite_data_envio")
    private LocalDateTime dataEnvio;

    public Invite() {}

    public Invite(Evento evento, Usuario convidado, EstadoInvite estado) {
        this.evento = evento;
        this.convidado = convidado;
        this.estado = estado;
        this.dataEnvio = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }

    public Usuario getConvidado() { return convidado; }
    public void setConvidado(Usuario convidado) { this.convidado = convidado; }

    public EstadoInvite getEstado() { return estado; }
    public void setEstado(EstadoInvite estado) { this.estado = estado; }

    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }
}

