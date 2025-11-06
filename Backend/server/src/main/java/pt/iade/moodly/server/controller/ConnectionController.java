package pt.iade.moodly.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.iade.moodly.server.model.Connection;
import pt.iade.moodly.server.model.Usuario;
import pt.iade.moodly.server.repository.ConnectionRepository;
import pt.iade.moodly.server.repository.UsuarioRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/connections")
public class ConnectionController {

    private final ConnectionRepository connectionRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ConnectionController(ConnectionRepository connectionRepository, UsuarioRepository usuarioRepository) {
        this.connectionRepository = connectionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Send a connection request between two users
    @PostMapping("/request")
    public ResponseEntity<?> sendConnection(@RequestBody Connection connection) {
        Optional<Usuario> user1 = usuarioRepository.findById(connection.getUser1Id());
        Optional<Usuario> user2 = usuarioRepository.findById(connection.getUser2Id());

        if (user1.isEmpty() || user2.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found for one or both IDs");
        }

        Connection savedConnection = connectionRepository.save(connection);
        return ResponseEntity.ok(savedConnection);
    }

    // Get all connections for a given user
    @GetMapping("/{userId}")
    public ResponseEntity<?> getConnections(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionRepository.findByUser1IdOrUser2Id(userId, userId));
    }

    // Optionally: delete or block a connection later
    @DeleteMapping("/{connectionId}")
    public ResponseEntity<?> deleteConnection(@PathVariable Long connectionId) {
        if (!connectionRepository.existsById(connectionId)) {
            return ResponseEntity.badRequest().body("Connection not found");
        }
        connectionRepository.deleteById(connectionId);
        return ResponseEntity.ok("Connection deleted");
    }
}
