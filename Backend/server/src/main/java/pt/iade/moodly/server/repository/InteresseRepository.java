package pt.iade.moodly.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.iade.moodly.server.model.Interesse;

import java.util.Optional;

public interface InteresseRepository extends JpaRepository<Interesse, Long> {
    Optional<Interesse> findByNome(String nome);
}


