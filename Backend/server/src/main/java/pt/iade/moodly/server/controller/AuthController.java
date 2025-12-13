package pt.iade.moodly.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pt.iade.moodly.server.model.Usuario;
import pt.iade.moodly.server.repository.UsuarioRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public static class SignupRequest {
        public String nome;
        public String email;
        public String password;
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        if (req.nome == null || req.nome.isBlank()
                || req.email == null || req.email.isBlank()
                || req.password == null || req.password.isBlank()) {
            return ResponseEntity.badRequest().body("Dados inválidos");
        }

        if (usuarioRepository.existsByEmail(req.email)) {
            return ResponseEntity.status(409).body("Email já registado");
        }

        Usuario user = new Usuario();
        user.setNome(req.nome);
        user.setEmail(req.email);
        user.setSenhaHash(passwordEncoder.encode(req.password));
        user.setFotoPerfil(null);

        usuarioRepository.save(user);

        Map<String, Object> resp = new HashMap<>();
        resp.put("id", user.getId());
        resp.put("nome", user.getNome());
        resp.put("email", user.getEmail());
        resp.put("fotoPerfil", user.getFotoPerfil());

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if (req.email == null || req.password == null) {
            return ResponseEntity.badRequest().body("Dados inválidos");
        }

        Usuario user = usuarioRepository.findByEmail(req.email)
                .orElse(null);

        if (user == null || !passwordEncoder.matches(req.password, user.getSenhaHash())) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("id", user.getId());
        resp.put("nome", user.getNome());
        resp.put("email", user.getEmail());
        resp.put("fotoPerfil", user.getFotoPerfil());

        return ResponseEntity.ok(resp);
    }
}
