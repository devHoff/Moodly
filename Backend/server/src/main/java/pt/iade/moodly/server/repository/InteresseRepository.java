package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.iade.moodly.server.model.Interesse;

import java.util.Optional;

@Repository
public interface InteresseRepository extends JpaRepository<Interesse, Long> {
    Optional<Interesse> findByNomeAndTipo(String nome, String tipo);
}

