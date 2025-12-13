package pt.iade.moodly.server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.iade.moodly.server.model.Connection;
import pt.iade.moodly.server.model.ConnectionEstado;
import pt.iade.moodly.server.model.EstadoConexao;
import pt.iade.moodly.server.model.Usuario;
import pt.iade.moodly.server.repository.ConnectionEstadoRepository;
import pt.iade.moodly.server.repository.ConnectionRepository;
import pt.iade.moodly.server.repository.EstadoConexaoRepository;
import pt.iade.moodly.server.repository.UsuarioRepository;

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

usuarioRepository.findById(dto.currentUserId)
        .orElseThrow(() -> new RuntimeException("User atual não encontrado"));
usuarioRepository.findById(dto.targetUserId)
        .orElseThrow(() -> new RuntimeException("User alvo não encontrado"));

EstadoConexao pendente = getEstadoOrThrow("pendente");
EstadoConexao aceite = getEstadoOrThrow("aceite");

ConnectionResponseDTO resp = new ConnectionResponseDTO();

Optional<Connection> sameDir = connectionRepository
        .findByUser1IdAndUser2Id(dto.currentUserId, dto.targetUserId);
if (sameDir.isPresent()) {
    Connection existing = sameDir.get();
    Optional<ConnectionEstado> lastEstadoOpt =
            connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(existing);
    if (lastEstadoOpt.isPresent()) {
        String st = lastEstadoOpt.get().getEstado().getDescricao();
        resp.connectionId = existing.getId();
        resp.status = st;
        resp.mutual = "aceite".equalsIgnoreCase(st);
        return ResponseEntity.ok(resp);
    }
}

Optional<Connection> opp = connectionRepository
        .findByUser1IdAndUser2Id(dto.targetUserId, dto.currentUserId);

if (opp.isPresent()) {
    Connection existing = opp.get();
    Optional<ConnectionEstado> lastEstadoOpt =
            connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(existing);

    if (lastEstadoOpt.isPresent()) {
        String st = lastEstadoOpt.get().getEstado().getDescricao();
        if ("pendente".equalsIgnoreCase(st)) {
            ConnectionEstado accepted = new ConnectionEstado(existing, aceite);
            connectionEstadoRepository.save(accepted);

            resp.connectionId = existing.getId();
            resp.status = "aceite";
            resp.mutual = true;
            return ResponseEntity.ok(resp);
        }
        if ("aceite".equalsIgnoreCase(st)) {
            resp.connectionId = existing.getId();
            resp.status = "aceite";
            resp.mutual = true;
            return ResponseEntity.ok(resp);
        }
        resp.connectionId = existing.getId();
        resp.status = st;
        resp.mutual = false;
        return ResponseEntity.ok(resp);
    }
}

Connection connection = new Connection(dto.currentUserId, dto.targetUserId);
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
    return ResponseEntity.badRequest().body("Dados inválidos");
}

Connection connection = connectionRepository.findById(connectionId)
        .orElseThrow(() -> new RuntimeException("Conexão não encontrada"));

if (!Objects.equals(connection.getUser2Id(), dto.userId)) {
    return ResponseEntity.status(403).body("Sem permissão");
}

EstadoConexao aceite = getEstadoOrThrow("aceite");
EstadoConexao recusado = getEstadoOrThrow("recusado");

String r = dto.resposta.trim().toLowerCase();
EstadoConexao novoEstado = r.equals("aceite") ? aceite : recusado;

ConnectionEstado ce = new ConnectionEstado(connection, novoEstado);
connectionEstadoRepository.save(ce);

return ResponseEntity.ok().build();
}

@GetMapping("/pending/{userId}")
public ResponseEntity<?> pendingConnections(@PathVariable Long userId) {
usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("User não encontrado"));

List<Connection> all = connectionRepository.findAllForUser(userId);

List<PendingConnectionDTO> out = new ArrayList<>();
for (Connection c : all) {
    Optional<ConnectionEstado> last = connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(c);
    if (last.isEmpty()) continue;

    String status = last.get().getEstado().getDescricao();
    if (!"pendente".equalsIgnoreCase(status)) continue;

    if (!Objects.equals(c.getUser2Id(), userId)) continue;

    Usuario other = usuarioRepository.findById(c.getUser1Id()).orElse(null);
    if (other == null) continue;

    PendingConnectionDTO dto = new PendingConnectionDTO();
    dto.connectionId = c.getId();
    dto.userId = other.getId();
    dto.nome = other.getNome();
    dto.fotoPerfil = other.getFotoPerfil();
    out.add(dto);
}

out.sort(Comparator.comparing(a -> a.nome == null ? "" : a.nome.toLowerCase()));
return ResponseEntity.ok(out);
}

@GetMapping("/outgoing/{userId}")
public ResponseEntity<?> outgoingConnections(@PathVariable Long userId) {
usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("User não encontrado"));

List<Connection> all = connectionRepository.findAllForUser(userId);

List<OutgoingConnectionDTO> out = new ArrayList<>();
for (Connection c : all) {
    Optional<ConnectionEstado> last = connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(c);
    if (last.isEmpty()) continue;

    String status = last.get().getEstado().getDescricao();
    if (!"pendente".equalsIgnoreCase(status) && !"aceite".equalsIgnoreCase(status)) continue;

    if (!Objects.equals(c.getUser1Id(), userId) && !"aceite".equalsIgnoreCase(status)) continue;

    Long otherId = Objects.equals(c.getUser1Id(), userId) ? c.getUser2Id() : c.getUser1Id();
    Usuario other = usuarioRepository.findById(otherId).orElse(null);
    if (other == null) continue;

    OutgoingConnectionDTO dto = new OutgoingConnectionDTO();
    dto.connectionId = c.getId();
    dto.userId = other.getId();
    dto.nome = other.getNome();
    dto.fotoPerfil = other.getFotoPerfil();
    dto.mutual = "aceite".equalsIgnoreCase(status);

    if ("pendente".equalsIgnoreCase(status) && !Objects.equals(c.getUser1Id(), userId)) continue;

    out.add(dto);
}

out.sort((a, b) -> {
    if (a.mutual != b.mutual) return a.mutual ? -1 : 1;
    String an = a.nome == null ? "" : a.nome.toLowerCase();
    String bn = b.nome == null ? "" : b.nome.toLowerCase();
    return an.compareTo(bn);
});

return ResponseEntity.ok(out);
}

@GetMapping("/mutual/{userId}")
public ResponseEntity<?> mutualConnections(@PathVariable Long userId) {
usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("User não encontrado"));

List<Connection> all = connectionRepository.findAllForUser(userId);

List<Usuario> out = new ArrayList<>();
for (Connection c : all) {
    Optional<ConnectionEstado> last = connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(c);
    if (last.isEmpty()) continue;

    String status = last.get().getEstado().getDescricao();
    if (!"aceite".equalsIgnoreCase(status)) continue;

    Long otherId = Objects.equals(c.getUser1Id(), userId) ? c.getUser2Id() : c.getUser1Id();
    Usuario other = usuarioRepository.findById(otherId).orElse(null);
    if (other != null) out.add(other);
}

out.sort(Comparator.comparing(u -> u.getNome() == null ? "" : u.getNome().toLowerCase()));
return ResponseEntity.ok(out);
}

@GetMapping("/discover/{userId}")
public ResponseEntity<?> discoverUsers(@PathVariable Long userId,
                                    @RequestParam(defaultValue = "20") int limit) {
usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("User não encontrado"));

List<Connection> allConns = connectionRepository.findAllForUser(userId);

Map<Long, String> currentStatus = new HashMap<>();
for (Connection c : allConns) {
    connectionEstadoRepository.findTopByConnectionOrderByDataRegistoDesc(c)
            .ifPresent(ce -> currentStatus.put(c.getId(), ce.getEstado().getDescricao()));
}

Set<Long> blocked = new HashSet<>();
Set<Long> pendingToCurrent = new HashSet<>();

for (Connection c : allConns) {
    String status = currentStatus.get(c.getId());
    if (status == null) continue;

    Long otherId = Objects.equals(c.getUser1Id(), userId) ? c.getUser2Id() : c.getUser1Id();

    if ("aceite".equalsIgnoreCase(status) || "recusado".equalsIgnoreCase(status)) {
        blocked.add(otherId);
    } else if ("pendente".equalsIgnoreCase(status)) {
        blocked.add(otherId);
        if (Objects.equals(c.getUser2Id(), userId)) {
            pendingToCurrent.add(c.getUser1Id());
        }
    }
}

List<Usuario> allUsers = usuarioRepository.findAll();

List<Usuario> priority = new ArrayList<>();
List<Usuario> candidates = new ArrayList<>();

for (Usuario u : allUsers) {
    if (u.getId() == null) continue;
    if (Objects.equals(u.getId(), userId)) continue;

    if (pendingToCurrent.contains(u.getId())) {
        priority.add(u);
        continue;
    }

    if (!blocked.contains(u.getId())) {
        candidates.add(u);
    }
}

Collections.shuffle(candidates);

List<Usuario> result = new ArrayList<>();
result.addAll(priority);
result.addAll(candidates);

if (result.size() > limit) {
    result = result.subList(0, limit);
}

return ResponseEntity.ok(result);
}
}

