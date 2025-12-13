package pt.iade.moodly.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.iade.moodly.server.model.*;
import pt.iade.moodly.server.repository.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    public static class ChatMessageDTO {
        public Long id;
        public Long autorId;
        public String autorNome;
        public String conteudo;
        public String dataEnvio;
    }

    public static class ConnectionChatPreviewDTO {
        public Long connectionId;
        public Long otherUserId;
        public String otherUserName;
        public String otherUserPhoto;
        public String lastMessage;
        public String lastMessageTime;
    }

    public static class EventChatDTO {
        public Long eventoId;
        public String titulo;
    }

    private ChatMessageDTO mapPostToDto(Post post) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.id = post.getId();
        dto.autorId = post.getAutor() != null ? post.getAutor().getId() : null;
        dto.autorNome = post.getAutor() != null ? post.getAutor().getNome() : null;
        dto.conteudo = post.getConteudo();
        LocalDateTime data = post.getDataEnvio();
        dto.dataEnvio = data != null ? data.toString() : null;
        return dto;
    }

    private ChatMessageDTO mapGroupPostToDto(GroupPost post) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.id = post.getId();
        dto.autorId = post.getAutor() != null ? post.getAutor().getId() : null;
        dto.autorNome = post.getAutor() != null ? post.getAutor().getNome() : null;
        dto.conteudo = post.getConteudo();
        LocalDateTime data = post.getDataEnvio();
        dto.dataEnvio = data != null ? data.toString() : null;
        return dto;
    }

    private EstadoConexao getEstadoAceite() {
        return estadoConexaoRepository.findByDescricao("aceite")
                .orElseThrow(() -> new RuntimeException("Estado 'aceite' não existe"));
    }

    private boolean isConnectionAccepted(Connection connection) {
        EstadoConexao estadoAceite = getEstadoAceite();
        Optional<ConnectionEstado> lastEstadoOpt =
                connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(connection);
        if (lastEstadoOpt.isEmpty()) return false;
        ConnectionEstado last = lastEstadoOpt.get();
        return last.getEstado() != null &&
                Objects.equals(last.getEstado().getId(), estadoAceite.getId());
    }

    @PostMapping("/connection/{connectionId}/send")
    public ResponseEntity<?> sendDirectMessage(@PathVariable Long connectionId,
                                               @RequestBody SendMessageDTO dto) {
        if (dto.autorId == null || dto.conteudo == null || dto.conteudo.isBlank()) {
            return ResponseEntity.badRequest().body("Dados inválidos");
        }

        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection não encontrada"));

        if (!isConnectionAccepted(connection)) {
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

        return ResponseEntity.ok(mapPostToDto(msg));
    }

    @GetMapping("/connection/{connectionId}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getDirectMessages(@PathVariable Long connectionId) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection não encontrada"));

        if (!isConnectionAccepted(connection)) {
            return ResponseEntity.status(403).build();
        }

        List<Post> messages = postRepository.findByConnectionOrderByDataEnvioAsc(connection);
        List<ChatMessageDTO> dtos = messages.stream()
                .map(this::mapPostToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/user/{userId}/connections")
    public ResponseEntity<List<ConnectionChatPreviewDTO>> getUserConnectionChats(
            @PathVariable Long userId) {

        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        List<Connection> allConnections = connectionRepository.findAll();
        List<ConnectionChatPreviewDTO> result = new ArrayList<>();

        for (Connection c : allConnections) {
            if (!Objects.equals(c.getUser1Id(), user.getId()) &&
                    !Objects.equals(c.getUser2Id(), user.getId())) {
                continue;
            }

            if (!isConnectionAccepted(c)) continue;

            Optional<Post> lastPostOpt = postRepository.findTopByConnectionOrderByDataEnvioDesc(c);
            if (lastPostOpt.isEmpty()) continue;

            Post lastPost = lastPostOpt.get();
            Long otherId = Objects.equals(c.getUser1Id(), user.getId())
                    ? c.getUser2Id() : c.getUser1Id();

            Usuario other = usuarioRepository.findById(otherId).orElse(null);
            if (other == null) continue;

            ConnectionChatPreviewDTO dto = new ConnectionChatPreviewDTO();
            dto.connectionId = c.getId();
            dto.otherUserId = other.getId();
            dto.otherUserName = other.getNome();
            dto.otherUserPhoto = other.getFotoPerfil();
            dto.lastMessage = lastPost.getConteudo();
            LocalDateTime data = lastPost.getDataEnvio();
            dto.lastMessageTime = data != null ? data.toString() : null;

            result.add(dto);
        }

        result.sort((a, b) -> {
            if (a.lastMessageTime == null && b.lastMessageTime == null) return 0;
            if (a.lastMessageTime == null) return 1;
            if (b.lastMessageTime == null) return -1;
            return b.lastMessageTime.compareTo(a.lastMessageTime);
        });

        return ResponseEntity.ok(result);
    }

    @PostMapping("/event/{eventId}/send")
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

        return ResponseEntity.ok(mapGroupPostToDto(msg));
    }

    @GetMapping("/event/{eventId}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getGroupMessages(@PathVariable Long eventId) {
        Evento evento = eventoRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        List<GroupPost> messages = groupPostRepository.findByEventoOrderByDataEnvioAsc(evento);
        List<ChatMessageDTO> dtos = messages.stream()
                .map(this::mapGroupPostToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/events-for-user/{userId}")
    public ResponseEntity<List<EventChatDTO>> getEventsForUser(@PathVariable Long userId) {
        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        List<Evento> criados = eventoRepository.findByCriador(user);
        List<Evento> convidado = eventoRepository.findByConvidado(user);

        Map<Long, Evento> map = new LinkedHashMap<>();
        for (Evento e : criados) {
            map.putIfAbsent(e.getId(), e);
        }
        for (Evento e : convidado) {
            map.putIfAbsent(e.getId(), e);
        }

        List<EventChatDTO> resp = new ArrayList<>();
        for (Evento e : map.values()) {
            EventChatDTO dto = new EventChatDTO();
            dto.eventoId = e.getId();
            dto.titulo = e.getTitulo();
            resp.add(dto);
        }

        return ResponseEntity.ok(resp);
    }
}
