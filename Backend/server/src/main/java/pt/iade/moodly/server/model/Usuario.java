package pt.iade.moodly.server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuar_id")
    private Long id;

    @Column(name = "usuar_nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "usuar_email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "usuar_senha_hash", nullable = false, length = 255)
    private String senhaHash;

    @Column(name = "usuar_foto_perfil", length = 255)
    private String fotoPerfil;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senhaHash) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }
}
