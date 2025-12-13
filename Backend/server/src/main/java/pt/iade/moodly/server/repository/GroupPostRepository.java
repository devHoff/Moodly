package pt.iade.moodly.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import pt.iade.moodly.server.model.Evento;
import pt.iade.moodly.server.model.GroupPost;

public interface GroupPostRepository extends JpaRepository<GroupPost, Long> {
    List<GroupPost> findByEventoOrderByDataEnvioAsc(Evento evento);
    Optional<GroupPost> findTopByEventoOrderByDataEnvioDesc(Evento evento);
    List<GroupPost> findByEvento(Evento evento);
    @Transactional
    void deleteByEvento(Evento evento);
}



