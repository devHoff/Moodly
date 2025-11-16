package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.iade.moodly.server.model.Estado;

import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Optional<Estado> findByDescricao(String descricao);
}
