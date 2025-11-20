package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.iade.moodly.server.model.EstadoConexao;

import java.util.Optional;

public interface EstadoConexaoRepository extends JpaRepository<EstadoConexao, Long> {
    Optional<EstadoConexao> findByDescricao(String descricao);
}

