package pt.iade.moodly.server.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "connections")
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "connect_id")
    private Long id;

    @Column(name = "connect_usuar1_id", nullable = false)
    private Long user1Id;

    @Column(name = "connect_usuar2_id", nullable = false)
    private Long user2Id;

    @Column(name = "connect_data_criacao", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    public Connection() {}

    public Connection(Long user1Id, Long user2Id) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.dataCriacao = LocalDateTime.now();
    }

    public Long getId() {return id;}

    public Long getUser1Id() {return user1Id;}
    public void setUser1Id(Long user1Id) { this.user1Id = user1Id; }

    public Long getUser2Id() {return user2Id;}
    public void setUser2Id(Long user2Id) {this.user2Id = user2Id;}

    public LocalDateTime getDataCriacao() {return dataCriacao;}
    public void setDataCriacao(LocalDateTime dataCriacao) {this.dataCriacao = dataCriacao;}
}
