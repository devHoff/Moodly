package pt.iade.moodly.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pt.iade.moodly.server.Dto.InterestDTO;
import pt.iade.moodly.server.Dto.UserProfileUpdateRequest;
import pt.iade.moodly.server.model.Interesse;
import pt.iade.moodly.server.model.Usuario;
import pt.iade.moodly.server.model.UsuarioInteresse;
import pt.iade.moodly.server.repository.InteresseRepository;
import pt.iade.moodly.server.repository.UsuarioInteresseRepository;
import pt.iade.moodly.server.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
public class ProfileController {

    private final UsuarioRepository usuarioRepo;
    private final InteresseRepository interesseRepo;
    private final UsuarioInteresseRepository usuarioInteresseRepo;

    public ProfileController(
            UsuarioRepository usuarioRepo,
            InteresseRepository interesseRepo,
            UsuarioInteresseRepository usuarioInteresseRepo
    ) {
        this.usuarioRepo = usuarioRepo;
        this.interesseRepo = interesseRepo;
        this.usuarioInteresseRepo = usuarioInteresseRepo;
    }

    @PutMapping("/{userId}")
    @Transactional
    public ResponseEntity<Void> updateProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileUpdateRequest request
    ) {
        // 1) Fetch user
        Usuario usuario = usuarioRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2) Update simple fields
        usuario.setFotoPerfil(request.getFotoPerfil());
        usuarioRepo.save(usuario); // explicit save ok

        // 3) Clear all existing links in one bulk op
        usuarioInteresseRepo.deleteAllByUsuarioId(userId);

        // 4) Re-insert interests
        if (request.getInteresses() != null) {
            for (InterestDTO interestDTO : request.getInteresses()) {
                if (interestDTO.getNome() == null || interestDTO.getNome().isBlank()) continue;

                // Get or create Interesse entity
                Interesse interesse = interesseRepo.findByNomeAndTipo(interestDTO.getNome(), interestDTO.getTipo())
                        .orElseGet(() -> {
                            Interesse novo = new Interesse();
                            novo.setNome(interestDTO.getNome());
                            novo.setTipo(interestDTO.getTipo());
                            return interesseRepo.save(novo);
                        });

                // Create link user <-> interest
                UsuarioInteresse link = new UsuarioInteresse();
                link.setUsuario(usuario);
                link.setInteresse(interesse);
                usuarioInteresseRepo.save(link);
            }
        }

        return ResponseEntity.noContent().build(); // 204
    }
}
