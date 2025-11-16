package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.iade.moodly.server.model.Connection;

import java.util.List;
import java.util.Optional;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    List<Connection> findByUser1IdOrUser2Id(Long user1Id, Long user2Id);

    Optional<Connection> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);

    @Query("SELECT c FROM Connection c WHERE (c.user1Id = :userId OR c.user2Id = :userId)")
    List<Connection> findAllForUser(@Param("userId") Long userId);
}
