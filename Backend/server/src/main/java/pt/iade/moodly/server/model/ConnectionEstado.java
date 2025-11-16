package pt.iade.moodly.server.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Connection_Estado")
public class ConnectionEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "connect_estado_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "connect_id", nullable = false)
    private Connection connection;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    @Column(name = "data_registo")
    private LocalDateTime dataRegisto;

    public ConnectionEstado() {}

    public ConnectionEstado(Connection connection, Estado estado) {
        this.connection = connection;
        this.estado = estado;
        this.dataRegisto = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDateTime getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(LocalDateTime dataRegisto) {
        this.dataRegisto = dataRegisto;
    }
}

