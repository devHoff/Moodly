package pt.iade.moodly.server.controller;

import jakarta.transaction.Transactional;
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
    private final SubinteresseRepository subinteresseRepo;

    public ProfileController(UsuarioRepository usuarioRepo,
                             UsuarioInteresseRepository usuarioInteresseRepo,
                             InteresseRepository interesseRepo,
                             SubinteresseRepository subinteresseRepo) {
        this.usuarioRepo = usuarioRepo;
        this.usuarioInteresseRepo = usuarioInteresseRepo;
        this.interesseRepo = interesseRepo;
        this.subinteresseRepo = subinteresseRepo;
    }

    // ---------- DTOs ----------

    public static class InterestDTO {
        private String tipo; // categoria: "musica", "filme"
        private String nome; // subinteresse: "Rock"

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

    // ---------- UPDATE PROFILE ----------

    @PutMapping("/{userId}")
    @Transactional
    public ResponseEntity<UserProfileResponseDTO> updateProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileUpdateRequest request
    ) {
        Usuario usuario = usuarioRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        usuario.setFotoPerfil(request.getFotoPerfil());

        // apagar ligações antigas
        usuarioInteresseRepo.deleteAllByUsuarioId(userId);

        if (request.getInteresses() != null) {
            for (InterestDTO dto : request.getInteresses()) {
                if (dto.getNome() == null || dto.getNome().isBlank()
                        || dto.getTipo() == null || dto.getTipo().isBlank()) {
                    continue;
                }

                // tipo -> Interesse (categoria)
                Interesse interesse = interesseRepo.findByNome(dto.getTipo())
                        .orElseGet(() -> interesseRepo.save(new Interesse(dto.getTipo())));

                // nome -> Subinteresse ligado a esse Interesse
                Subinteresse sub = subinteresseRepo.findByNomeAndInteresse(dto.getNome(), interesse)
                        .orElseGet(() -> subinteresseRepo.save(
                                new Subinteresse(interesse, dto.getNome())
                        ));

                UsuarioInteresse ui = new UsuarioInteresse();
                ui.setUsuario(usuario);
                ui.setSubinteresse(sub);
                usuarioInteresseRepo.save(ui);
            }
        }

        usuarioRepo.save(usuario);

        // construir resposta
        UserProfileResponseDTO resp = new UserProfileResponseDTO();
        resp.setId(usuario.getId());
        resp.setNome(usuario.getNome());
        resp.setEmail(usuario.getEmail());
        resp.setFotoPerfil(usuario.getFotoPerfil());

        List<UsuarioInteresse> links = usuarioInteresseRepo.findByUsuarioId(userId);
        List<InterestDTO> interessesDTO = new ArrayList<>();
        for (UsuarioInteresse ui : links) {
            Subinteresse s = ui.getSubinteresse();
            if (s != null && s.getInteresse() != null) {
                interessesDTO.add(new InterestDTO(
                        s.getInteresse().getNome(),
                        s.getNome()
                ));
            }
        }
        resp.setInteresses(interessesDTO);

        return ResponseEntity.ok(resp);
    }
}
