package pt.iade.moodly.server.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.iade.moodly.server.model.*;
import pt.iade.moodly.server.repository.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
public class ProfileController {

    private final UsuarioRepository usuarioRepo;
    private final UsuarioInteresseRepository usuarioInteresseRepo;
    private final InteresseRepository interesseRepo;

    @Autowired
    public ProfileController(
            UsuarioRepository usuarioRepo,
            UsuarioInteresseRepository usuarioInteresseRepo,
            InteresseRepository interesseRepo
    ) {
        this.usuarioRepo = usuarioRepo;
        this.usuarioInteresseRepo = usuarioInteresseRepo;
        this.interesseRepo = interesseRepo;
    }

    // -----------------------------
    // DTOs
    // -----------------------------
    public static class InterestDTO {
        private String tipo;
        private String nome;

        public InterestDTO() {}

        public InterestDTO(String tipo, String nome) {
            this.tipo = tipo;
            this.nome = nome;
        }

        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
    }

    public static class UserProfileUpdateRequest {
        private String fotoPerfil;
        private List<InterestDTO> interesses;

        public String getFotoPerfil() { return fotoPerfil; }
        public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

        public List<InterestDTO> getInteresses() { return interesses; }
        public void setInteresses(List<InterestDTO> interesses) { this.interesses = interesses; }
    }

    public static class UserProfileResponseDTO {
        private Long id;
        private String nome;
        private String email;
        private String fotoPerfil;
        private List<InterestDTO> interesses;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getFotoPerfil() { return fotoPerfil; }
        public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

        public List<InterestDTO> getInteresses() { return interesses; }
        public void setInteresses(List<InterestDTO> interesses) { this.interesses = interesses; }
    }

    // -----------------------------
    // ATUALIZAR PERFIL (200 OK + DTO)
    // -----------------------------
    @PutMapping("/{userId}")
    @Transactional
    public ResponseEntity<UserProfileResponseDTO> updateProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileUpdateRequest request
    ) {
        Usuario usuario = usuarioRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Atualizar foto
        usuario.setFotoPerfil(request.getFotoPerfil());

        // Remover interesses antigos (ligação usuário-interesse)
        usuarioInteresseRepo.deleteAllByUsuarioId(userId);

        // Adicionar novos interesses
        if (request.getInteresses() != null) {
            for (InterestDTO interestDTO : request.getInteresses()) {
                if (interestDTO.getNome() == null || interestDTO.getNome().isBlank()) continue;

                // Buscar ou criar interesse
                Interesse interesse = interesseRepo.findByNomeAndTipo(
                                interestDTO.getNome(),
                                interestDTO.getTipo()
                        )
                        .orElseGet(() -> {
                            Interesse novo = new Interesse();
                            novo.setNome(interestDTO.getNome());
                            novo.setTipo(interestDTO.getTipo());
                            return interesseRepo.save(novo);
                        });

                UsuarioInteresse link = new UsuarioInteresse();
                link.setUsuario(usuario);
                link.setInteresse(interesse);
                usuarioInteresseRepo.save(link);
            }
        }

        usuarioRepo.save(usuario);

        // Construir DTO de resposta SEM expor diretamente o entity (evita LazyInitialization)
        UserProfileResponseDTO resp = new UserProfileResponseDTO();
        resp.setId(usuario.getId());
        resp.setNome(usuario.getNome());
        resp.setEmail(usuario.getEmail());
        resp.setFotoPerfil(usuario.getFotoPerfil());

        // Buscar interesses via UsuarioInteresseRepository (em vez de usuario.getInteresses())
        List<UsuarioInteresse> links = usuarioInteresseRepo.findByUsuarioId(userId);
        List<InterestDTO> interessesDTO = new ArrayList<>();
        for (UsuarioInteresse ui : links) {
            Interesse i = ui.getInteresse();
            if (i != null) {
                interessesDTO.add(new InterestDTO(i.getTipo(), i.getNome()));
            }
        }
        resp.setInteresses(interessesDTO);

        return ResponseEntity.ok(resp);
    }
}
