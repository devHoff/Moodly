package pt.iade.moodly.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.iade.moodly.server.model.Usuario;
import pt.iade.moodly.server.repository.UsuarioRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        // For now, store password directly (hashing can come later)
        Usuario saved = usuarioRepository.save(usuario);
        return ResponseEntity.ok(saved);
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Optional<Usuario> found = usuarioRepository.findByEmail(usuario.getEmail());

        if (found.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        Usuario existing = found.get();
        if (!existing.getSenhaHash().equals(usuario.getSenhaHash())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        return ResponseEntity.ok(existing);
    }
    // 🔹 Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<Usuario> user = usuarioRepository.findById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Update user info
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Usuario updatedUser) {
        return usuarioRepository.findById(id).map(user -> {
            if (updatedUser.getNome() != null)
                user.setNome(updatedUser.getNome());
            if (updatedUser.getFotoPerfil() != null)
                user.setFotoPerfil(updatedUser.getFotoPerfil());
            if (updatedUser.getSenhaHash() != null && !updatedUser.getSenhaHash().isEmpty())
                user.setSenhaHash(updatedUser.getSenhaHash()); // hash if needed

            usuarioRepository.save(user);
            return ResponseEntity.ok(user);
        }).orElse(ResponseEntity.notFound().build());
    }
    

}
