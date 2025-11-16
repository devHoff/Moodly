package pt.iade.moodly.server.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Invite")
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invite_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "convidado_id", nullable = false)
    private Usuario convidado;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    public Invite() {}

    public Invite(Evento evento, Usuario convidado, Estado estado) {
        this.evento = evento;
        this.convidado = convidado;
        this.estado = estado;
        this.dataEnvio = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Usuario getConvidado() {
        return convidado;
    }

    public void setConvidado(Usuario convidado) {
        this.convidado = convidado;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}
