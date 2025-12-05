package pt.iade.moodly.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.iade.moodly.server.model.Connection;
import pt.iade.moodly.server.model.ConnectionEstado;
import pt.iade.moodly.server.model.EstadoConexao;
import pt.iade.moodly.server.model.Usuario;
import pt.iade.moodly.server.repository.ConnectionEstadoRepository;
import pt.iade.moodly.server.repository.ConnectionRepository;
import pt.iade.moodly.server.repository.EstadoConexaoRepository;
import pt.iade.moodly.server.repository.UsuarioRepository;

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

    public static class RespondConnectionDTO {
        public Long userId;
        public String resposta;
    }

    public static class PendingConnectionDTO {
        public Long connectionId;
        public Long userId;
        public String nome;
        public String fotoPerfil;
    }

    public static class OutgoingConnectionDTO {
        public Long connectionId;
        public Long userId;
        public String nome;
        public String fotoPerfil;
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

        Connection connection = new Connection(current.getId(), target.getId());
        connectionRepository.save(connection);

        ConnectionEstado ce = new ConnectionEstado(connection, pendente);
        connectionEstadoRepository.save(ce);

        resp.connectionId = connection.getId();
        resp.status = "pendente";
        resp.mutual = false;

        return ResponseEntity.ok(resp);
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

        EstadoConexao novoEstado = getEstadoOrThrow(dto.resposta.toLowerCase());
        ConnectionEstado ce = new ConnectionEstado(connection, novoEstado);
        connectionEstadoRepository.save(ce);

        return ResponseEntity.ok("Estado atualizado para " + dto.resposta);
    }

    @GetMapping("/mutual/{userId}")
    public ResponseEntity<List<Usuario>> mutualConnections(@PathVariable Long userId) {
        EstadoConexao aceite = getEstadoOrThrow("aceite");
        List<Connection> allConns = connectionRepository.findAll();
        Set<Long> added = new HashSet<>();
        List<Usuario> result = new ArrayList<>();

        for (Connection c : allConns) {
            Optional<ConnectionEstado> lastEstadoOpt =
                    connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(c);
            if (lastEstadoOpt.isEmpty()) continue;
            if (!Objects.equals(lastEstadoOpt.get().getEstado().getId(), aceite.getId())) continue;

            Long otherId = null;
            if (c.getUser1Id().equals(userId)) {
                otherId = c.getUser2Id();
            } else if (c.getUser2Id().equals(userId)) {
                otherId = c.getUser1Id();
            }
            if (otherId == null) continue;
            if (added.contains(otherId)) continue;

            Usuario other = usuarioRepository.findById(otherId).orElse(null);
            if (other != null) {
                added.add(otherId);
                result.add(other);
            }
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<List<PendingConnectionDTO>> pendingConnections(@PathVariable Long userId) {
        EstadoConexao pendente = getEstadoOrThrow("pendente");
        List<Connection> allConns = connectionRepository.findAll();
        List<PendingConnectionDTO> result = new ArrayList<>();

        for (Connection c : allConns) {
            if (!c.getUser2Id().equals(userId)) continue;

            Optional<ConnectionEstado> lastEstadoOpt =
                    connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(c);
            if (lastEstadoOpt.isEmpty()) continue;
            if (!Objects.equals(lastEstadoOpt.get().getEstado().getId(), pendente.getId())) continue;

            Long otherId = c.getUser1Id();
            Usuario other = usuarioRepository.findById(otherId).orElse(null);
            if (other == null) continue;

            PendingConnectionDTO dto = new PendingConnectionDTO();
            dto.connectionId = c.getId();
            dto.userId = other.getId();
            dto.nome = other.getNome();
            dto.fotoPerfil = other.getFotoPerfil();

            result.add(dto);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/outgoing/{userId}")
    public ResponseEntity<List<OutgoingConnectionDTO>> outgoingConnections(@PathVariable Long userId) {
        EstadoConexao pendente = getEstadoOrThrow("pendente");
        EstadoConexao aceite = getEstadoOrThrow("aceite");

        List<Connection> connections = connectionRepository.findByUser1Id(userId);
        List<OutgoingConnectionDTO> result = new ArrayList<>();

        for (Connection c : connections) {
            Optional<ConnectionEstado> lastEstadoOpt =
                    connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(c);
            if (lastEstadoOpt.isEmpty()) continue;

            ConnectionEstado last = lastEstadoOpt.get();
            Long estadoId = last.getEstado().getId();

            boolean isPending = Objects.equals(estadoId, pendente.getId());
            boolean isMutual = Objects.equals(estadoId, aceite.getId());

            if (!isPending && !isMutual) continue;

            Long otherId = c.getUser2Id();
            Usuario other = usuarioRepository.findById(otherId).orElse(null);
            if (other == null) continue;

            OutgoingConnectionDTO dto = new OutgoingConnectionDTO();
            dto.connectionId = c.getId();
            dto.userId = other.getId();
            dto.nome = other.getNome();
            dto.fotoPerfil = other.getFotoPerfil();
            dto.mutual = isMutual;

            result.add(dto);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/discover/{userId}")
    public ResponseEntity<List<Usuario>> discoverUsers(
            @PathVariable Long userId,
            @RequestParam(name = "limit", defaultValue = "20") int limit
    ) {
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
