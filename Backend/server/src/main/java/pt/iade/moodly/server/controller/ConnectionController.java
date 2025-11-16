package pt.iade.moodly.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.iade.moodly.server.model.*;
import pt.iade.moodly.server.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/connections")
@CrossOrigin(origins = "*")
public class ConnectionController {

    private final ConnectionRepository connectionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConnectionEstadoRepository connectionEstadoRepository;
    private final EstadoRepository estadoRepository;

    @Autowired
    public ConnectionController(ConnectionRepository connectionRepository,
                                UsuarioRepository usuarioRepository,
                                ConnectionEstadoRepository connectionEstadoRepository,
                                EstadoRepository estadoRepository) {
        this.connectionRepository = connectionRepository;
        this.usuarioRepository = usuarioRepository;
        this.connectionEstadoRepository = connectionEstadoRepository;
        this.estadoRepository = estadoRepository;
    }

    private Estado getEstadoOrThrow(String descricao) {
        return estadoRepository.findByDescricao(descricao)
                .orElseThrow(() -> new RuntimeException("Estado '" + descricao + "' não existe na BD"));
    }

    public static class ConnectionRequestDTO {
        public Long currentUserId;
        public Long targetUserId;
    }

    public static class ConnectionResponseDTO {
        public Long connectionId;
        public String status; // pendente / aceite / recusado / bloqueado / espera
        public boolean mutual;
    }

    @PostMapping("/request")
    public ResponseEntity<?> sendConnection(@RequestBody ConnectionRequestDTO dto) {
        if (dto.currentUserId == null || dto.targetUserId == null) {
            return ResponseEntity.badRequest().body("IDs inválidos");
        }
        if (dto.currentUserId.equals(dto.targetUserId)) {
            return ResponseEntity.badRequest().body("Não pode conectar consigo mesmo");
        }

        Usuario current = usuarioRepository.findById(dto.currentUserId)
                .orElseThrow(() -> new RuntimeException("User atual não encontrado"));
        Usuario target = usuarioRepository.findById(dto.targetUserId)
                .orElseThrow(() -> new RuntimeException("User alvo não encontrado"));

        Estado pendente = getEstadoOrThrow("pendente");
        Estado aceite = getEstadoOrThrow("aceite");

        // 1) Ver se já há um pedido do outro user para mim (outro já swipou para a direita)
        Optional<Connection> opp = connectionRepository
                .findByUser1IdAndUser2Id(dto.targetUserId, dto.currentUserId);

        ConnectionResponseDTO resp = new ConnectionResponseDTO();

        if (opp.isPresent()) {
            Connection existing = opp.get();
            Optional<ConnectionEstado> lastEstadoOpt =
                    connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(existing);

            if (lastEstadoOpt.isPresent() &&
                    "pendente".equalsIgnoreCase(lastEstadoOpt.get().getEstado().getDescricao())) {
                // Agora fica aceite (match mútuo)
                ConnectionEstado accepted = new ConnectionEstado(existing, aceite);
                connectionEstadoRepository.save(accepted);

                resp.connectionId = existing.getId();
                resp.status = "aceite";
                resp.mutual = true;
                return ResponseEntity.ok(resp);
            } else if (lastEstadoOpt.isPresent() &&
                    "aceite".equalsIgnoreCase(lastEstadoOpt.get().getEstado().getDescricao())) {
                resp.connectionId = existing.getId();
                resp.status = "aceite";
                resp.mutual = true;
                return ResponseEntity.ok(resp);
            }
        }

        // 2) Ver se já existe um pedido meu para ele
        Optional<Connection> sameDir = connectionRepository
                .findByUser1IdAndUser2Id(dto.currentUserId, dto.targetUserId);
        if (sameDir.isPresent()) {
            Connection existing = sameDir.get();
            Optional<ConnectionEstado> lastEstadoOpt =
                    connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(existing);
            if (lastEstadoOpt.isPresent()) {
                resp.connectionId = existing.getId();
                resp.status = lastEstadoOpt.get().getEstado().getDescricao();
                resp.mutual = "aceite".equalsIgnoreCase(resp.status);
                return ResponseEntity.ok(resp);
            }
        }

        // 3) Criar nova ligação pendente
        Connection connection = new Connection(current.getId(), target.getId());
        connectionRepository.save(connection);

        ConnectionEstado ce = new ConnectionEstado(connection, pendente);
        connectionEstadoRepository.save(ce);

        resp.connectionId = connection.getId();
        resp.status = "pendente";
        resp.mutual = false;

        return ResponseEntity.ok(resp);
    }

    public static class RespondConnectionDTO {
        public Long userId;
        public String resposta; // "aceite" ou "recusado"
    }

    @PostMapping("/{connectionId}/respond")
    public ResponseEntity<?> respondConnection(@PathVariable Long connectionId,
                                               @RequestBody RespondConnectionDTO dto) {
        if (dto.userId == null || dto.resposta == null) {
            return ResponseEntity.badRequest().body("Dados incompletos");
        }

        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection não encontrada"));

        // Só quem recebeu o pedido (user2) pode responder
        if (!connection.getUser2Id().equals(dto.userId)) {
            return ResponseEntity.status(403).body("Apenas o utilizador convidado pode responder ao pedido");
        }

        Optional<ConnectionEstado> lastEstadoOpt =
                connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(connection);
        if (lastEstadoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Estado da ligação não encontrado");
        }

        String respostaNorm = dto.resposta.toLowerCase(Locale.ROOT);
        if (!respostaNorm.equals("aceite") && !respostaNorm.equals("recusado")) {
            return ResponseEntity.badRequest().body("Resposta inválida (use 'aceite' ou 'recusado')");
        }

        Estado estado = getEstadoOrThrow(respostaNorm);
        ConnectionEstado ce = new ConnectionEstado(connection, estado);
        connectionEstadoRepository.save(ce);

        Map<String, Object> resp = new HashMap<>();
        resp.put("connectionId", connectionId);
        resp.put("status", estado.getDescricao());
        return ResponseEntity.ok(resp);
    }

    // Ligações mútuas (estado atual = aceite)
    @GetMapping("/mutual/{userId}")
    public ResponseEntity<?> getMutualConnections(@PathVariable Long userId) {
        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        List<Connection> all = connectionRepository.findAllForUser(user.getId());
        Estado estadoAceite = getEstadoOrThrow("aceite");

        List<Usuario> mutuals = new ArrayList<>();

        for (Connection c : all) {
            Optional<ConnectionEstado> lastEstadoOpt =
                    connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(c);
            if (lastEstadoOpt.isPresent() &&
                    lastEstadoOpt.get().getEstado().getId().equals(estadoAceite.getId())) {
                Long otherId = c.getUser1Id().equals(user.getId()) ? c.getUser2Id() : c.getUser1Id();
                usuarioRepository.findById(otherId).ifPresent(mutuals::add);
            }
        }

        return ResponseEntity.ok(mutuals);
    }

    // Pedidos pendentes recebidos pelo user (ainda não respondeu)
    @GetMapping("/pending/{userId}")
    public ResponseEntity<?> getPendingRequests(@PathVariable Long userId) {
        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        Estado estadoPendente = getEstadoOrThrow("pendente");
        List<Connection> all = connectionRepository.findAllForUser(user.getId());

        List<Map<String, Object>> result = new ArrayList<>();

        for (Connection c : all) {
            if (!c.getUser2Id().equals(user.getId())) {
                continue; // apenas pedidos em que sou user2 (fui convidado)
            }

            Optional<ConnectionEstado> lastEstadoOpt =
                    connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(c);

            if (lastEstadoOpt.isPresent() &&
                    lastEstadoOpt.get().getEstado().getId().equals(estadoPendente.getId())) {

                Long otherId = c.getUser1Id();
                Optional<Usuario> otherOpt = usuarioRepository.findById(otherId);
                if (otherOpt.isPresent()) {
                    Usuario other = otherOpt.get();
                    Map<String, Object> item = new HashMap<>();
                    item.put("connectionId", c.getId());
                    item.put("userId", other.getId());
                    item.put("nome", other.getNome());
                    item.put("fotoPerfil", other.getFotoPerfil());
                    result.add(item);
                }
            }
        }

        return ResponseEntity.ok(result);
    }

    // Discover: perfis aleatórios, com prioridade para quem já me mandou pedido pendente
    @GetMapping("/discover/{userId}")
    public ResponseEntity<?> discoverUsers(@PathVariable Long userId,
                                           @RequestParam(defaultValue = "20") int limit) {
        Usuario current = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        Estado pendente = getEstadoOrThrow("pendente");
        Estado aceite = getEstadoOrThrow("aceite");

        List<Connection> allConns = connectionRepository.findAll();

        // map connectionId -> estadoAtual
        Map<Long, String> currentStatus = new HashMap<>();
        for (Connection c : allConns) {
            connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(c)
                    .ifPresent(ce -> currentStatus.put(c.getId(), ce.getEstado().getDescricao()));
        }

        Set<Long> alreadyConnectedOrPending = new HashSet<>();
        Set<Long> pendingToCurrent = new HashSet<>();

        for (Connection c : allConns) {
            String status = currentStatus.get(c.getId());
            if (status == null) continue;

            Long other1 = c.getUser1Id();
            Long other2 = c.getUser2Id();

            if (other1.equals(userId) || other2.equals(userId)) {
                Long other = other1.equals(userId) ? other2 : other1;

                if ("aceite".equalsIgnoreCase(status) || "recusado".equalsIgnoreCase(status)) {
                    alreadyConnectedOrPending.add(other);
                } else if ("pendente".equalsIgnoreCase(status)) {
                    alreadyConnectedOrPending.add(other);
                    // pedidos que recebi (eles são user1, eu user2)
                    if (c.getUser2Id().equals(userId)) {
                        pendingToCurrent.add(c.getUser1Id());
                    }
                }
            }
        }

        List<Usuario> allUsers = usuarioRepository.findAll();
        List<Usuario> result = new ArrayList<>();

        // 1º prioridade: quem já me mandou pedido pendente
        for (Usuario u : allUsers) {
            if (u.getId().equals(userId)) continue;
            if (pendingToCurrent.contains(u.getId())) {
                result.add(u);
            }
        }

        // depois outros users sem qualquer ligação
        for (Usuario u : allUsers) {
            if (u.getId().equals(userId)) continue;
            if (result.contains(u)) continue;
            if (!alreadyConnectedOrPending.contains(u.getId())) {
                result.add(u);
            }
        }

        if (result.size() > limit) {
            result = result.subList(0, limit);
        }

        return ResponseEntity.ok(result);
    }
}
