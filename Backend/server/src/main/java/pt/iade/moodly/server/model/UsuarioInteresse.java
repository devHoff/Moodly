package pt.iade.moodly.server.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Usuario_Interesse")
public class UsuarioInteresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuar_interes_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuar_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "interes_id", nullable = false)
    private Interesse interesse;

    // Getters/setters
    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Interesse getInteresse() { return interesse; }
    public void setInteresse(Interesse interesse) { this.interesse = interesse; }
}

