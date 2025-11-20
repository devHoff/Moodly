package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.iade.moodly.server.model.EstadoInvite;
import pt.iade.moodly.server.model.Evento;
import pt.iade.moodly.server.model.Invite;
import pt.iade.moodly.server.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    List<Invite> findByEvento(Evento evento);

    List<Invite> findByConvidado(Usuario convidado);

    Optional<Invite> findByEventoAndConvidado(Evento evento, Usuario convidado);

    @Query("SELECT i FROM Invite i WHERE i.convidado = :user AND i.estado = :estado")
    List<Invite> findByConvidadoAndEstado(@Param("user") Usuario user, @Param("estado") EstadoInvite estado);
}


