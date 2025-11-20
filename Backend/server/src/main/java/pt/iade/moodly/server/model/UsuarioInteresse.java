package pt.iade.moodly.server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario_interesse")
public class UsuarioInteresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usint_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usint_usuar_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "usint_subinter_id", nullable = false)
    private Subinteresse subinteresse;

    public UsuarioInteresse() {}

    public Long getId() { return id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Subinteresse getSubinteresse() { return subinteresse; }
    public void setSubinteresse(Subinteresse subinteresse) { this.subinteresse = subinteresse; }
}


