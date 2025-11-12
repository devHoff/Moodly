package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.iade.moodly.server.model.UsuarioInteresse;

@Repository
public interface UsuarioInteresseRepository extends JpaRepository<UsuarioInteresse, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM UsuarioInteresse ui WHERE ui.usuario.id = :userId")
    void deleteAllByUsuarioId(@Param("userId") Long userId);
}

