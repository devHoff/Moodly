package pt.iade.moodly.server.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_connect_id", nullable = false)
    private Connection connection;

    @ManyToOne
    @JoinColumn(name = "post_usuar_id", nullable = false)
    private Usuario autor;

    @Column(name = "post_conteudo", nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    @Column(name = "post_data_envio")
    private LocalDateTime dataEnvio;

    public Post() {}

    public Post(Connection connection, Usuario autor, String conteudo) {
        this.connection = connection;
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataEnvio = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public Connection getConnection() { return connection; }
    public void setConnection(Connection connection) { this.connection = connection; }

    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }
}

