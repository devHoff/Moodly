package pt.iade.moodly.server.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pt.iade.moodly.server.model.Usuario;
import pt.iade.moodly.server.repository.UsuarioRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public static class AuthRequest {
        public String nome;     // only for signup
        public String email;
        public String password; // plain
    }

    public static class AuthResponse {
        public boolean ok;
        public String message;
        public Long userId;
        public String nome;
        public String email;
        public String fotoPerfil;

        public static AuthResponse of(boolean ok, String msg, Usuario u) {
            AuthResponse r = new AuthResponse();
            r.ok = ok;
            r.message = msg;
            if (u != null) {
                r.userId = u.getId();
                r.nome = u.getNome();
                r.email = u.getEmail();
                r.fotoPerfil = u.getFotoPerfil();
            }
            return r;
        }
    }

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody AuthRequest req) {
        if (req.email == null || req.password == null || req.nome == null) {
            return AuthResponse.of(false, "Dados incompletos", null);
        }
        if (usuarioRepository.findByEmail(req.email).isPresent()) {
            return AuthResponse.of(false, "Email já registado", null);
        }
        Usuario u = new Usuario();
        u.setNome(req.nome);
        u.setEmail(req.email);
        u.setSenhaHash(passwordEncoder.encode(req.password));
        usuarioRepository.save(u);
        return AuthResponse.of(true, "Conta criada com sucesso", u);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        Optional<Usuario> found = usuarioRepository.findByEmail(req.email);
        if (found.isEmpty()) {
            return AuthResponse.of(false, "Email não encontrado", null);
        }
        Usuario u = found.get();
        if (!passwordEncoder.matches(req.password, u.getSenhaHash())) {
            return AuthResponse.of(false, "Senha incorreta", null);
        }
        return AuthResponse.of(true, "Login bem-sucedido", u);
    }
}
