package pt.iade.moodly.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.iade.moodly.server.model.Interesse;
import pt.iade.moodly.server.model.Subinteresse;
import pt.iade.moodly.server.model.Usuario;
import pt.iade.moodly.server.model.UsuarioInteresse;
import pt.iade.moodly.server.repository.UsuarioInteresseRepository;
import pt.iade.moodly.server.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioInteresseRepository usuarioInteresseRepository;

    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository,
                             UsuarioInteresseRepository usuarioInteresseRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioInteresseRepository = usuarioInteresseRepository;
    }

    public static class InteresseDto {
        private String tipo;
        private String nome;

        public InteresseDto() {
        }

        public InteresseDto(String tipo, String nome) {
            this.tipo = tipo;
            this.nome = nome;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }

    public static class UsuarioResponseDto {
        private Long id;
        private String nome;
        private String email;
        private String fotoPerfil;
        private List<InteresseDto> interesses;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFotoPerfil() {
            return fotoPerfil;
        }

        public void setFotoPerfil(String fotoPerfil) {
            this.fotoPerfil = fotoPerfil;
        }

        public List<InteresseDto> getInteresses() {
            return interesses;
        }

        public void setInteresses(List<InteresseDto> interesses) {
            this.interesses = interesses;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getUserById(@PathVariable Long id) {
        Optional<Usuario> optUser = usuarioRepository.findById(id);
        if (optUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario user = optUser.get();

        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(user.getId());
        dto.setNome(user.getNome());
        dto.setEmail(user.getEmail());
        dto.setFotoPerfil(user.getFotoPerfil());

        List<UsuarioInteresse> links = usuarioInteresseRepository.findByUsuarioId(id);
        List<InteresseDto> interessesDto = new ArrayList<>();

        for (UsuarioInteresse ui : links) {
            Subinteresse sub = ui.getSubinteresse();
            if (sub == null) {
                continue;
            }
            Interesse interesse = sub.getInteresse();
            String tipo = interesse != null ? interesse.getNome() : null;
            String nome = sub.getNome();
            if (tipo != null && nome != null) {
                interessesDto.add(new InteresseDto(tipo, nome));
            }
        }

        dto.setInteresses(interessesDto);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody Usuario updatedUser
    ) {
        Optional<Usuario> optUser = usuarioRepository.findById(id);
        if (optUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario user = optUser.get();

        if (updatedUser.getNome() != null) {
            user.setNome(updatedUser.getNome());
        }
        if (updatedUser.getFotoPerfil() != null) {
            user.setFotoPerfil(updatedUser.getFotoPerfil());
        }

        usuarioRepository.save(user);

        return getUserById(id);
    }
}
