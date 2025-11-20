package pt.iade.moodly.server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "subinteresse")
public class Subinteresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subinter_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subinter_inter_id", nullable = false)
    private Interesse interesse;

    @Column(name = "subinter_nome", nullable = false, length = 100)
    private String nome; // exemplo: "Rock", "Valorant"

    public Subinteresse() {}

    public Subinteresse(Interesse interesse, String nome) {
        this.interesse = interesse;
        this.nome = nome;
    }

    public Long getId() { return id; }

    public Interesse getInteresse() { return interesse; }
    public void setInteresse(Interesse interesse) { this.interesse = interesse; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}

