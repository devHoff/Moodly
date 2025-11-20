package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.iade.moodly.server.model.Connection;
import pt.iade.moodly.server.model.ConnectionEstado;

import java.util.List;
import java.util.Optional;

public interface ConnectionEstadoRepository extends JpaRepository<ConnectionEstado, Long> {

    Optional<ConnectionEstado> findTopByConnectionOrderByDataRegistoDesc(Connection connection);

    List<ConnectionEstado> findByConnection(Connection connection);
}


