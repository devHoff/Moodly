package pt.iade.moodly.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.iade.moodly.server.model.*;
import pt.iade.moodly.server.repository.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InviteRepository inviteRepository;
    private final EstadoInviteRepository estadoInviteRepository;

    public EventController(EventoRepository eventoRepository,
                           UsuarioRepository usuarioRepository,
                           InviteRepository inviteRepository,
                           EstadoInviteRepository estadoInviteRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.inviteRepository = inviteRepository;
        this.estadoInviteRepository = estadoInviteRepository;
    }

    private EstadoInvite getEstadoInviteOrThrow(String nome) {
        return estadoInviteRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("EstadoInvite '" + nome + "' não existe"));
    }

    public static class CreateEventDTO {
        public Long criadorId;
        public String titulo;
        public String descricao;
        public String local;
        public LocalDateTime dataEvento;
        public List<Long> convidadosIds;
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody CreateEventDTO dto) {
        if (dto.criadorId == null || dto.titulo == null || dto.titulo.isBlank()) {
            return ResponseEntity.badRequest().body("Dados inválidos");
        }

        Usuario criador = usuarioRepository.findById(dto.criadorId)
                .orElseThrow(() -> new RuntimeException("Criador não encontrado"));

        Evento evento = new Evento();
        evento.setCriador(criador);
        evento.setTitulo(dto.titulo);
        evento.setDescricao(dto.descricao);
        evento.setLocal(dto.local);
        evento.setDataEvento(dto.dataEvento);
        eventoRepository.save(evento);

        EstadoInvite pendente = getEstadoInviteOrThrow("pendente");

        if (dto.convidadosIds != null) {
            for (Long convidadoId : dto.convidadosIds) {
                Usuario convidado = usuarioRepository.findById(convidadoId)
                        .orElseThrow(() -> new RuntimeException("Convidado " + convidadoId + " não encontrado"));
                Invite invite = new Invite(evento, convidado, pendente);
                inviteRepository.save(invite);
            }
        }

        return ResponseEntity.ok(evento);
    }

    public static class RespondInviteDTO {
        public Long userId;
        public String resposta; // "aceite" ou "recusado"
    }

    @PostMapping("/{eventId}/respond")
    public ResponseEntity<?> respondInvite(@PathVariable Long eventId,
                                           @RequestBody RespondInviteDTO dto) {
        if (dto.userId == null || dto.resposta == null) {
            return ResponseEntity.badRequest().body("Dados inválidos");
        }

        Evento evento = eventoRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        Usuario user = usuarioRepository.findById(dto.userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        Invite invite = inviteRepository.findByEventoAndConvidado(evento, user)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));

        String respostaNorm = dto.resposta.toLowerCase(Locale.ROOT);
        if (!respostaNorm.equals("aceite") && !respostaNorm.equals("recusado")) {
            return ResponseEntity.badRequest().body("Resposta inválida (use 'aceite' ou 'recusado')");
        }

        EstadoInvite estado = getEstadoInviteOrThrow(respostaNorm);
        invite.setEstado(estado);
        inviteRepository.save(invite);

        Map<String, Object> resp = new HashMap<>();
        resp.put("inviteId", invite.getId());
        resp.put("estado", estado.getNome());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/user/{userId}")
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

    @GetMapping("/invites/{userId}")
    public ResponseEntity<?> getInvitesForUser(@PathVariable Long userId) {
        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        List<Invite> invites = inviteRepository.findByConvidado(user);
        return ResponseEntity.ok(invites);
    }
}

