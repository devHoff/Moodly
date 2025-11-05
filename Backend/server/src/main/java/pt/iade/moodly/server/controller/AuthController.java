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

    // 🟢 SIGNUP
    @PostMapping("/signup")
    public String signup(@RequestBody Usuario user) {
        if (usuarioRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email já registado!";
        }

        user.setSenhaHash(passwordEncoder.encode(user.getSenhaHash()));
        usuarioRepository.save(user);
        return "Conta criada com sucesso!";
    }

    // 🔵 LOGIN
    @PostMapping("/login")
    public String login(@RequestBody Usuario user) {
        Optional<Usuario> foundUser = usuarioRepository.findByEmail(user.getEmail());
        if (foundUser.isEmpty()) {
            return "Email não encontrado!";
        }

        if (passwordEncoder.matches(user.getSenhaHash(), foundUser.get().getSenhaHash())) {
            return "Login bem-sucedido!";
        } else {
            return "Senha incorreta!";
        }
    }
}

