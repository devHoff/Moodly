package pt.iade.moodly.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    private final ConnectionRepository connectionRepository;
    private final PostRepository postRepository;
    private final GroupPostRepository groupPostRepository;

    public EventController(EventoRepository eventoRepository,
                           UsuarioRepository usuarioRepository,
                           InviteRepository inviteRepository,
                           EstadoInviteRepository estadoInviteRepository,
                           ConnectionRepository connectionRepository,
                           PostRepository postRepository,
                           GroupPostRepository groupPostRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.inviteRepository = inviteRepository;
        this.estadoInviteRepository = estadoInviteRepository;
        this.connectionRepository = connectionRepository;
        this.postRepository = postRepository;
        this.groupPostRepository = groupPostRepository;
    }

    private EstadoInvite estado(String nome) {
        return estadoInviteRepository.findByNomeIgnoreCase(nome)
                .orElseGet(() -> {
                    EstadoInvite e = new EstadoInvite();
                    e.setNome(nome);
                    return estadoInviteRepository.save(e);
                });
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody CreateEventDTO dto) {
        Usuario criador = usuarioRepository.findById(dto.criadorId)
                .orElseThrow(() -> new RuntimeException("Criador não encontrado"));

        Evento evento = new Evento();
        evento.setCriador(criador);
        evento.setTitulo(dto.titulo);
        evento.setDescricao(dto.descricao);
        evento.setLocal(dto.local);
        evento.setDataEvento(LocalDateTime.parse(dto.dataEvento));
        eventoRepository.save(evento);

        EstadoInvite pend = estado("pendente");

        for (Long convidadoId : dto.convidadosIds) {
            if (convidadoId == null) continue;
            if (Objects.equals(convidadoId, criador.getId())) continue;

            Usuario convidado = usuarioRepository.findById(convidadoId).orElseThrow();
            Invite inv = new Invite(evento, convidado, pend);
            inviteRepository.save(inv);

            Optional<Connection> conn = connectionRepository
                    .findByUser1IdAndUser2Id(criador.getId(), convidado.getId());
            if (conn.isEmpty()) {
                conn = connectionRepository
                        .findByUser1IdAndUser2Id(convidado.getId(), criador.getId());
            }

            if (conn.isPresent()) {
                String c = "[EVENT_INVITE]" + evento.getId() + "|" + evento.getTitulo() + "|clique para participar";
                postRepository.save(new Post(conn.get(), criador, c));
            }
        }

        return ResponseEntity.ok(evento);
    }

    @PostMapping("/{eventId}/accept/{userId}")
    public ResponseEntity<?> accept(@PathVariable Long eventId, @PathVariable Long userId) {
        Evento e = eventoRepository.findById(eventId).orElseThrow();
        Usuario u = usuarioRepository.findById(userId).orElseThrow();
        Invite inv = inviteRepository.findByEventoAndConvidado(e, u).orElseThrow();
        inv.setEstado(estado("aceite"));
        inviteRepository.save(inv);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{eventId}/leave/{userId}")
    public ResponseEntity<?> leave(@PathVariable Long eventId, @PathVariable Long userId) {
        Evento e = eventoRepository.findById(eventId).orElseThrow();
        Usuario u = usuarioRepository.findById(userId).orElseThrow();
        Invite inv = inviteRepository.findByEventoAndConvidado(e, u).orElseThrow();
        inv.setEstado(estado("recusado"));
        inviteRepository.save(inv);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @PostMapping("/{eventId}/cancel/{userId}")
    public ResponseEntity<?> cancel(@PathVariable Long eventId, @PathVariable Long userId) {
        Evento e = eventoRepository.findById(eventId).orElseThrow();
        if (e.getCriador() == null || !Objects.equals(e.getCriador().getId(), userId))
            return ResponseEntity.status(403).build();

        EstadoInvite canc = estado("cancelado");

        List<Invite> invites = inviteRepository.findByEvento(e);
        for (Invite inv : invites) {
            inv.setEstado(canc);
        }
        inviteRepository.saveAll(invites);

        groupPostRepository.deleteByEvento(e);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{eventId}/hide/{userId}")
    public ResponseEntity<?> hide(@PathVariable Long eventId, @PathVariable Long userId) {
        Evento e = eventoRepository.findById(eventId).orElseThrow();
        inviteRepository.findByEventoAndConvidado(e, usuarioRepository.findById(userId).orElseThrow())
                .ifPresent(inviteRepository::delete);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> eventsForUser(@PathVariable Long userId) {
        Usuario u = usuarioRepository.findById(userId).orElseThrow();
        List<Evento> criados = eventoRepository.findByCriador(u);
        List<Invite> invited = inviteRepository.findByConvidado(u);

        List<EventDTO> out = new ArrayList<>();

        for (Evento e : criados) {
            Optional<Invite> invCriadorOpt = inviteRepository.findByEventoAndConvidado(e, u);

            String estadoFinal;
            if (invCriadorOpt.isPresent() && invCriadorOpt.get().getEstado() != null) {
                estadoFinal = invCriadorOpt.get().getEstado().getNome();
            } else {
                boolean cancelado = inviteRepository.findByEvento(e).stream()
                        .anyMatch(i -> i.getEstado() != null && i.getEstado().getNome() != null
                                && i.getEstado().getNome().equalsIgnoreCase("cancelado"));
                estadoFinal = cancelado ? "cancelado" : "aceite";
            }

            out.add(new EventDTO(e, estadoFinal, true));
        }

        for (Invite inv : invited) {
            Evento e = inv.getEvento();
            if (out.stream().noneMatch(x -> x.id.equals(e.getId()))) {
                out.add(new EventDTO(
                        e,
                        inv.getEstado() != null ? inv.getEstado().getNome() : "pendente",
                        false
                ));
            }
        }

        return ResponseEntity.ok(out);
    }

    public static class CreateEventDTO {
        public Long criadorId;
        public String titulo;
        public String descricao;
        public String local;
        public String dataEvento;
        public List<Long> convidadosIds;
    }

    public static class EventDTO {
        public Long id;
        public String titulo;
        public String descricao;
        public String local;
        public String dataEvento;
        public String estado;
        public boolean isOwner;
        public String criadorNome;

        public EventDTO(Evento e, String estado, boolean owner) {
            this.id = e.getId();
            this.titulo = e.getTitulo();
            this.descricao = e.getDescricao();
            this.local = e.getLocal();
            this.dataEvento = e.getDataEvento() != null ? e.getDataEvento().toString() : null;
            this.estado = estado;
            this.isOwner = owner;
            this.criadorNome = e.getCriador() != null ? e.getCriador().getNome() : null;
        }
    }
}
