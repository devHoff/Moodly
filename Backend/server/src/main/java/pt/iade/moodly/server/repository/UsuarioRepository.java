package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.iade.moodly.server.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}


