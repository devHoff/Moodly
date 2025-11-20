package pt.iade.moodly.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.iade.moodly.server.model.*;
import pt.iade.moodly.server.repository.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chats")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ConnectionRepository connectionRepository;
    private final ConnectionEstadoRepository connectionEstadoRepository;
    private final EstadoConexaoRepository estadoConexaoRepository;
    private final PostRepository postRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final InviteRepository inviteRepository;
    private final GroupPostRepository groupPostRepository;
    private final EstadoInviteRepository estadoInviteRepository;

    public ChatController(ConnectionRepository connectionRepository,
                          ConnectionEstadoRepository connectionEstadoRepository,
                          EstadoConexaoRepository estadoConexaoRepository,
                          PostRepository postRepository,
                          UsuarioRepository usuarioRepository,
                          EventoRepository eventoRepository,
                          InviteRepository inviteRepository,
                          GroupPostRepository groupPostRepository,
                          EstadoInviteRepository estadoInviteRepository) {
        this.connectionRepository = connectionRepository;
        this.connectionEstadoRepository = connectionEstadoRepository;
        this.estadoConexaoRepository = estadoConexaoRepository;
        this.postRepository = postRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.inviteRepository = inviteRepository;
        this.groupPostRepository = groupPostRepository;
        this.estadoInviteRepository = estadoInviteRepository;
    }

    public static class SendMessageDTO {
        public Long autorId;
        public String conteudo;
    }

    // ----- 1-1 Chat -----

    @PostMapping("/connection/{connectionId}")
    public ResponseEntity<?> sendDirectMessage(@PathVariable Long connectionId,
                                               @RequestBody SendMessageDTO dto) {
        if (dto.autorId == null || dto.conteudo == null || dto.conteudo.isBlank()) {
            return ResponseEntity.badRequest().body("Dados inválidos");
        }

        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection não encontrada"));

        EstadoConexao estadoAceite = estadoConexaoRepository.findByDescricao("aceite")
                .orElseThrow(() -> new RuntimeException("Estado 'aceite' não existe"));

        ConnectionEstado lastEstado = connectionEstadoRepository
                .findTopByConnectionOrderByDataRegistoDesc(connection)
                .orElseThrow(() -> new RuntimeException("Estado da ligação não encontrado"));

        if (!lastEstado.getEstado().getId().equals(estadoAceite.getId())) {
            return ResponseEntity.status(403).body("Ligação ainda não aceite");
        }

        if (!connection.getUser1Id().equals(dto.autorId) &&
                !connection.getUser2Id().equals(dto.autorId)) {
            return ResponseEntity.status(403).body("Autor não pertence a esta ligação");
        }

        Usuario autor = usuarioRepository.findById(dto.autorId)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        Post msg = new Post(connection, autor, dto.conteudo);
        postRepository.save(msg);

        return ResponseEntity.ok(msg);
    }

    @GetMapping("/connection/{connectionId}")
    public ResponseEntity<?> getDirectMessages(@PathVariable Long connectionId) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection não encontrada"));

        List<Post> messages = postRepository.findByConnectionOrderByDataEnvioAsc(connection);
        return ResponseEntity.ok(messages);
    }

    // ----- Group Chat (Eventos) -----

    @PostMapping("/event/{eventId}")
    public ResponseEntity<?> sendGroupMessage(@PathVariable Long eventId,
                                              @RequestBody SendMessageDTO dto) {
        if (dto.autorId == null || dto.conteudo == null || dto.conteudo.isBlank()) {
            return ResponseEntity.badRequest().body("Dados inválidos");
        }

        Evento evento = eventoRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        Usuario autor = usuarioRepository.findById(dto.autorId)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        boolean allowed = false;

        if (evento.getCriador().getId().equals(autor.getId())) {
            allowed = true;
        } else {
            var inviteOpt = inviteRepository.findByEventoAndConvidado(evento, autor);
            if (inviteOpt.isPresent()) {
                Invite inv = inviteOpt.get();
                if (inv.getEstado() != null &&
                        "aceite".equalsIgnoreCase(inv.getEstado().getNome())) {
                    allowed = true;
                }
            }
        }

        if (!allowed) {
            return ResponseEntity.status(403).body("Utilizador não tem acesso ao chat deste evento");
        }

        GroupPost msg = new GroupPost(evento, autor, dto.conteudo);
        groupPostRepository.save(msg);

        return ResponseEntity.ok(msg);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<?> getGroupMessages(@PathVariable Long eventId) {
        Evento evento = eventoRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        List<GroupPost> messages = groupPostRepository.findByEventoOrderByDataEnvioAsc(evento);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/events-for-user/{userId}")
    public ResponseEntity<?> getEventsForUser(@PathVariable Long userId) {
        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        List<Evento> criados = eventoRepository.findByCriador(user);
        List<Evento> convidado = eventoRepository.findByConvidado(user);

        Map<String, Object> resp = new HashMap<>();
        resp.put("criados", criados);
        resp.put("convidado", convidado);
        return ResponseEntity.ok(resp);
    }
}
