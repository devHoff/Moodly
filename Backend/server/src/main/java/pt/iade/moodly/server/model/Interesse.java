package pt.iade.moodly.server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "interesse")
public class Interesse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inter_id")
    private Long id;

    @Column(name = "inter_nome", nullable = false, length = 60)
    private String nome;

    public Interesse() {}

    public Interesse(String nome) {
        this.nome = nome;
    }

    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}

