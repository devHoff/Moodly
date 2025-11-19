package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.iade.moodly.server.model.UsuarioInteresse;

import java.util.List;

@Repository
public interface UsuarioInteresseRepository extends JpaRepository<UsuarioInteresse, Long> {

    // Buscar todos os interesses associados a um usuário
    List<UsuarioInteresse> findByUsuarioId(Long usuarioId);

    // Apagar todos os interesses do usuário
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM UsuarioInteresse ui WHERE ui.usuario.id = :userId")
    void deleteAllByUsuarioId(@Param("userId") Long userId);
}
