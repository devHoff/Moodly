package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.iade.moodly.server.model.Evento;
import pt.iade.moodly.server.model.Usuario;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByCriador(Usuario criador);

    @Query("SELECT DISTINCT i.evento FROM Invite i WHERE i.convidado = :user")
    List<Evento> findByConvidado(@Param("user") Usuario convidado);
}


