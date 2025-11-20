package pt.iade.moodly.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.iade.moodly.server.model.*;
import pt.iade.moodly.server.repository.*;

import java.util.*;

@RestController
@RequestMapping("/api/connections")
@CrossOrigin(origins = "*")
public class ConnectionController {

    private final ConnectionRepository connectionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConnectionEstadoRepository connectionEstadoRepository;
    private final EstadoConexaoRepository estadoConexaoRepository;

    public ConnectionController(ConnectionRepository connectionRepository,
                                UsuarioRepository usuarioRepository,
                                ConnectionEstadoRepository connectionEstadoRepository,
                                EstadoConexaoRepository estadoConexaoRepository) {
        this.connectionRepository = connectionRepository;
        this.usuarioRepository = usuarioRepository;
        this.connectionEstadoRepository = connectionEstadoRepository;
        this.estadoConexaoRepository = estadoConexaoRepository;
    }

    private EstadoConexao getEstadoOrThrow(String descricao) {
        return estadoConexaoRepository.findByDescricao(descricao)
                .orElseThrow(() -> new RuntimeException("Estado '" + descricao + "' não existe na BD"));
    }

    public static class ConnectionRequestDTO {
        public Long currentUserId;
        public Long targetUserId;
    }

    public static class ConnectionResponseDTO {
        public Long connectionId;
        public String status;
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

        EstadoConexao pendente = getEstadoOrThrow("pendente");
        EstadoConexao aceite = getEstadoOrThrow("aceite");

        ConnectionResponseDTO resp = new ConnectionResponseDTO();

        // 1) Já existe pedido na direcção inversa?
        Optional<Connection> opp = connectionRepository
                .findByUser1IdAndUser2Id(dto.targetUserId, dto.currentUserId);

        if (opp.isPresent()) {
            Connection existing = opp.get();
            Optional<ConnectionEstado> lastEstadoOpt =
                    connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(existing);

            if (lastEstadoOpt.isPresent() &&
                    "pendente".equalsIgnoreCase(lastEstadoOpt.get().getEstado().getDescricao())) {
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

        // 2) Já existe pedido na mesma direcção?
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

        Optional<Connection> connectionOpt = connectionRepository.findById(connectionId);
        if (connectionOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Connection não encontrada");
        }
        Connection connection = connectionOpt.get();

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

        EstadoConexao estado = getEstadoOrThrow(respostaNorm);
        ConnectionEstado ce = new ConnectionEstado(connection, estado);
        connectionEstadoRepository.save(ce);

        Map<String, Object> resp = new HashMap<>();
        resp.put("connectionId", connectionId);
        resp.put("status", estado.getDescricao());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/mutual/{userId}")
    public ResponseEntity<?> getMutualConnections(@PathVariable Long userId) {
        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        List<Connection> all = connectionRepository.findAllForUser(user.getId());
        EstadoConexao estadoAceite = getEstadoOrThrow("aceite");

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

    @GetMapping("/pending/{userId}")
    public ResponseEntity<?> getPendingRequests(@PathVariable Long userId) {
        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        EstadoConexao estadoPendente = getEstadoOrThrow("pendente");
        List<Connection> all = connectionRepository.findAllForUser(user.getId());

        List<Map<String, Object>> result = new ArrayList<>();

        for (Connection c : all) {
            if (!c.getUser2Id().equals(user.getId())) continue;

            Optional<ConnectionEstado> lastEstadoOpt =
                    connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(c);

            if (lastEstadoOpt.isPresent() &&
                    lastEstadoOpt.get().getEstado().getId().equals(estadoPendente.getId())) {

                Long otherId = c.getUser1Id();
                usuarioRepository.findById(otherId).ifPresent(other -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("connectionId", c.getId());
                    item.put("userId", other.getId());
                    item.put("nome", other.getNome());
                    item.put("fotoPerfil", other.getFotoPerfil());
                    result.add(item);
                });
            }
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/discover/{userId}")
    public ResponseEntity<?> discoverUsers(@PathVariable Long userId,
                                           @RequestParam(defaultValue = "20") int limit) {
        Usuario current = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        EstadoConexao pendente = getEstadoOrThrow("pendente");

        List<Connection> allConns = connectionRepository.findAll();

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
                    if (c.getUser2Id().equals(userId)) {
                        pendingToCurrent.add(c.getUser1Id());
                    }
                }
            }
        }

        List<Usuario> allUsers = usuarioRepository.findAll();
        List<Usuario> result = new ArrayList<>();

        for (Usuario u : allUsers) {
            if (u.getId().equals(userId)) continue;
            if (pendingToCurrent.contains(u.getId())) {
                result.add(u);
            }
        }

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
