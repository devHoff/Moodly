package pt.iade.moodly.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.iade.moodly.server.model.Interesse;
import pt.iade.moodly.server.model.Subinteresse;

public interface SubinteresseRepository extends JpaRepository<Subinteresse, Long> {
    Optional<Subinteresse> findByNomeAndInteresse(String nome, Interesse interesse);
}

