package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.iade.moodly.server.model.UsuarioInteresse;

import java.util.List;

public interface UsuarioInteresseRepository extends JpaRepository<UsuarioInteresse, Long> {
    List<UsuarioInteresse> findByUsuarioId(Long usuarioId);
    void deleteAllByUsuarioId(Long usuarioId);
}

