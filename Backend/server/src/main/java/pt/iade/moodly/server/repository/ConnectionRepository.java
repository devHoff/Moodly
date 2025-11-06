package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.iade.moodly.server.model.Connection;
import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    List<Connection> findByUser1IdOrUser2Id(Long user1Id, Long user2Id);
}
