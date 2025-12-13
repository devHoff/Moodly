package pt.iade.moodly.server.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pt.iade.moodly.server.Dto.InterestDTO;
import pt.iade.moodly.server.Dto.UserProfileUpdateRequest;
import pt.iade.moodly.server.model.Interesse;
import pt.iade.moodly.server.model.Subinteresse;
import pt.iade.moodly.server.model.Usuario;
import pt.iade.moodly.server.model.UsuarioInteresse;
import pt.iade.moodly.server.repository.InteresseRepository;
import pt.iade.moodly.server.repository.SubinteresseRepository;
import pt.iade.moodly.server.repository.UsuarioInteresseRepository;
import pt.iade.moodly.server.repository.UsuarioRepository;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
public class ProfileController {

    private final UsuarioRepository usuarioRepo;
    private final UsuarioInteresseRepository usuarioInteresseRepo;
    private final InteresseRepository interesseRepo;
    private final SubinteresseRepository subinteresseRepo;

    @Value("${moodly.upload-dir}")
    private String uploadDir;

    public ProfileController(
            UsuarioRepository usuarioRepo,
            UsuarioInteresseRepository usuarioInteresseRepo,
            InteresseRepository interesseRepo,
            SubinteresseRepository subinteresseRepo
    ) {
        this.usuarioRepo = usuarioRepo;
        this.usuarioInteresseRepo = usuarioInteresseRepo;
        this.interesseRepo = interesseRepo;
        this.subinteresseRepo = subinteresseRepo;
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

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponseDTO> getProfile(@PathVariable Long userId) {
        Usuario usuario = usuarioRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileResponseDTO resp = new UserProfileResponseDTO();
        resp.setId(usuario.getId());
        resp.setNome(usuario.getNome());
        resp.setEmail(usuario.getEmail());
        resp.setFotoPerfil(usuario.getFotoPerfil());

        List<UsuarioInteresse> links = usuarioInteresseRepo.findByUsuarioId(userId);
        List<InterestDTO> interessesDTO = new ArrayList<>();

        for (UsuarioInteresse ui : links) {
            Subinteresse s = ui.getSubinteresse();
            if (s != null) {
                Interesse interesse = s.getInteresse();
                String tipo = interesse != null ? interesse.getNome() : null;
                String nome = s.getNome();
                if (tipo != null && nome != null) {
                    InterestDTO dto = new InterestDTO();
                    dto.setTipo(tipo);
                    dto.setNome(nome);
                    interessesDTO.add(dto);
                }
            }
        }

        resp.setInteresses(interessesDTO);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{userId}")
    @Transactional
    public ResponseEntity<UserProfileResponseDTO> updateProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileUpdateRequest request
    ) {
        Usuario usuario = usuarioRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        usuario.setFotoPerfil(request.getFotoPerfil());
        usuarioRepo.save(usuario);

        usuarioInteresseRepo.deleteAllByUsuarioId(userId);

        List<InterestDTO> interesses = request.getInteresses();
        if (interesses != null) {
            for (InterestDTO dto : interesses) {
                if (dto.getTipo() == null || dto.getNome() == null) continue;

                String tipoLower = dto.getTipo().toLowerCase();
                Optional<Interesse> optInteresse = interesseRepo.findByNome(tipoLower);
                Interesse interesse = optInteresse.orElseGet(() -> {
                    Interesse novo = new Interesse(tipoLower);
                    return interesseRepo.save(novo);
                });

                Optional<Subinteresse> optSub = subinteresseRepo.findByNomeAndInteresse(dto.getNome(), interesse);
                Subinteresse sub;
                if (optSub.isPresent()) {
                    sub = optSub.get();
                } else {
                    sub = new Subinteresse();
                    sub.setNome(dto.getNome());
                    sub.setInteresse(interesse);
                    sub = subinteresseRepo.save(sub);
                }

                UsuarioInteresse ui = new UsuarioInteresse();
                ui.setUsuario(usuario);
                ui.setSubinteresse(sub);
                usuarioInteresseRepo.save(ui);
            }
        }

        return getProfile(userId);
    }

    @PostMapping("/{userId}/foto-perfil")
    @Transactional
    public ResponseEntity<UserProfileResponseDTO> uploadFotoPerfil(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        Usuario usuario = usuarioRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Path root = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path profileDir = root.resolve("profile");
        Files.createDirectories(profileDir);

        String original = file.getOriginalFilename();
        String ext = "";
        if (original != null && original.contains(".")) {
            ext = original.substring(original.lastIndexOf("."));
        }

        String fileName = "user-" + userId + "-" + System.currentTimeMillis() + ext;
        Path dest = profileDir.resolve(fileName);

        Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

        String fotoUrl = "/uploads/profile/" + fileName;
        usuario.setFotoPerfil(fotoUrl);
        usuarioRepo.save(usuario);

        return getProfile(userId);
    }
}
