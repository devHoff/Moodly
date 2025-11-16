package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.iade.moodly.server.model.Evento;
import pt.iade.moodly.server.model.GroupPost;

import java.util.List;

public interface GroupPostRepository extends JpaRepository<GroupPost, Long> {

    List<GroupPost> findByEventoOrderByDataEnvioAsc(Evento evento);
}
