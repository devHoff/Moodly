package pt.iade.moodly.server.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evento_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_usuar_id", nullable = false)
    private Usuario criador;

    @Column(name = "evento_titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "evento_descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "evento_local", length = 200)
    private String local;

    @Column(name = "evento_data")
    private LocalDateTime dataEvento;

    public Evento() {}

    public Long getId() {return id; }

    public Usuario getCriador() {return criador;}
    public void setCriador(Usuario criador) { this.criador = criador; }

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getLocal() {return local;}
    public void setLocal(String local) { this.local = local; }

    public LocalDateTime getDataEvento() {return dataEvento;}
    public void setDataEvento(LocalDateTime dataEvento) { this.dataEvento = dataEvento; }
}
