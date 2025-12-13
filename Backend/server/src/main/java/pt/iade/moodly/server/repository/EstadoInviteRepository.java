package pt.iade.moodly.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.iade.moodly.server.model.EstadoInvite;

public interface EstadoInviteRepository extends JpaRepository<EstadoInvite, Long> {
    Optional<EstadoInvite> findByNome(String nome);
    Optional<EstadoInvite> findByNomeIgnoreCase(String nome);
}
