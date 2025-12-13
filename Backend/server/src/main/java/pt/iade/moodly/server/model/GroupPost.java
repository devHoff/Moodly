package pt.iade.moodly.server.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_post")
public class GroupPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gp_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gp_evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "gp_usuar_id", nullable = false)
    private Usuario autor;

    @Column(name = "gp_conteudo", nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    @Column(name = "gp_data_envio")
    private LocalDateTime dataEnvio;

    public GroupPost() {}

    public GroupPost(Evento evento, Usuario autor, String conteudo) {
        this.evento = evento;
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataEnvio = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }

    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }
}
