package pt.iade.moodly.server.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "connection_estado")
public class ConnectionEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnt_est_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cnt_est_connect_id", nullable = false)
    private Connection connection;

    @ManyToOne
    @JoinColumn(name = "cnt_est_estadoc_id", nullable = false)
    private EstadoConexao estado;

    @Column(name = "cnt_est_data_registo")
    private LocalDateTime dataRegisto;

    public ConnectionEstado() {}

    public ConnectionEstado(Connection connection, EstadoConexao estado) {
        this.connection = connection;
        this.estado = estado;
        this.dataRegisto = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public Connection getConnection() { return connection; }
    public void setConnection(Connection connection) { this.connection = connection; }

    public EstadoConexao getEstado() { return estado; }
    public void setEstado(EstadoConexao estado) { this.estado = estado; }

    public LocalDateTime getDataRegisto() { return dataRegisto; }
    public void setDataRegisto(LocalDateTime dataRegisto) { this.dataRegisto = dataRegisto; }
}
