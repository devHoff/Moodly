package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.iade.moodly.server.model.EstadoInvite;

import java.util.Optional;

public interface EstadoInviteRepository extends JpaRepository<EstadoInvite, Long> {

    Optional<EstadoInvite> findByNome(String nome);
}
