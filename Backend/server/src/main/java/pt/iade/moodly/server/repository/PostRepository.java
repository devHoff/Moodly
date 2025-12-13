package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.iade.moodly.server.model.Connection;
import pt.iade.moodly.server.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByConnectionOrderByDataEnvioAsc(Connection connection);

    Optional<Post> findTopByConnectionOrderByDataEnvioDesc(Connection connection);
}



