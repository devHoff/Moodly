

insert into Usuario (nome, foto_perfil) values
('Delma Costa', 'https://picsum.photos/seed/delma/400/400'),
('Miguel Duarte','https://picsum.photos/seed/miguel/400/400'),
('Sara Monteiro','https://picsum.photos/seed/sara/400/400'),
('Rui Alves','https://picsum.photos/seed/rui/400/400'),
('Beatriz Fonseca','https://picsum.photos/seed/beatriz/400/400');

insert into Interesse (tipo, nome) values
('musica','Taylor Swift'),
('musica','The Weeknd'),
('filme','Vingadores: Endgame'),
('filme','Interestelar'),
('jogo','The legend of Zelda'),
('jogo','Super Mario Odyssey'),
('musica','Billie Eilish'),
('filme','Spiderman: Across the Spider-Verse'),
('jogo','Fortnite'),
('outro', 'Fotografia');

insert into Usuario_Interesse (usuar_id, interes_id) values
(1, 1), (1, 3), (1, 5),
(2, 2), (2, 4), (2, 9),
(3, 1), (3, 7), (3, 8),
(4, 3), (4, 6), (5, 5),
(5, 10);

insert into Estado (descricao) values
('pendente'),
('aceite'),
('recusado'),
('bloqueado'),
('espera');

insert into Connections (usuar1_id, usuar2_id, data_criacao) values
(1, 2, now()),
(1, 3, now()),
(2, 4, now()),
(3, 5, now());

insert into Connection_Estado (connect_id, estado_id, data_registo) values
(1, 2, now()),
(2, 1, now()),
(3, 2, now()),
(4, 1, now());

insert into Post (connect_id, autor_id, conteudo) values
(1, 1, 'Olá Miguel! Gostei das tuas músicas'),
(1, 2, 'Obrigado Delma! Também curtes Taylor Swift?'),
(2, 1, 'Oi Sara! Viste o novo fime dos Vingadores?'),
(3, 2, 'Rui, bora marcar uma sessão de cinema?'),
(4, 3, 'Beatriz, adoro as tuas fotos');

insert into Evento (criador_id, titulo, descricao, local, data_evento) values
(1, 'Noite de Karaoke', 'Evento para fãs de música cantarem juntos', 'Lisboa - Bairro Alto', '2025-11-20 21:00:00'),
(3, 'Cinema ao Ar Livre', 'Sessão de filmes com pipocas e convívio', 'Parque das Nações', '2025-11-22 20:30:00'),
(5, 'Noite de jogos', 'Sessão de gamimg em grupo', 'Odivelas', '2025-12-01 18:00:00');

insert into Invite (evento_id, convidado_id, estado_id) values
(1, 2, 1),
(1, 3, 2),
(2, 1, 1),
(2, 4, 2),
(3, 2, 1);

insert into Group_Post (evento_id, autor_id, conteudo) values
(1, 1, 'Malta, quero boa disposição'),
(1, 2, 'Posso levar o microfone extra!'),
(2, 3, 'Que filme acham devemos ver primeiro?'),
(2, 4, 'Spider-Verse seria top!'),
(3, 5, 'Alguém quer trazer comando extra?');
