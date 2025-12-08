package pt.iade.moodly.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pt.iade.moodly.server.repository.UsuarioRepository;

import java.io.IOException;
import java.nio.file.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfilePhotoController {

    private final UsuarioRepository usuarioRepository;

    public ProfilePhotoController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Value("${moodly.upload-dir}")
    private String uploadDir;

    @PostMapping("/{id}/photo")
    public ResponseEntity<?> uploadProfilePhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Ficheiro vazio");
        }

    
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath); 

        String originalName = file.getOriginalFilename();
        String ext = StringUtils.getFilenameExtension(originalName);
        String fileName = "user-" + id + "-" + System.currentTimeMillis()
                + (ext != null ? "." + ext : "");

        Path target = uploadPath.resolve(fileName);
        Files.createDirectories(target.getParent());
        file.transferTo(target.toFile());


        String publicUrl = "/uploads/profile/" + fileName;

        var user = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado"));
        user.setFotoPerfil(publicUrl);
        usuarioRepository.save(user);

        return ResponseEntity.ok(Map.of("url", publicUrl));
    }
}
