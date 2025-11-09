--  Todos os usuários e os seus interesses
select u.nome as usuário,
i.nome as interesse
from Usuario u
left join Usuario_Interesse ui on u.usuar_id = ui.usuar_id
left join Interesse i on ui.interes_id = i.interes_id
order by u.nome, i.nome;

-- Interesses em comum com a Delma Costa (id = 1)
select u2.nome as outro_usuário,
       i.nome as interesse_comum
from Usuario_Interesse ui1
join Usuario_Interesse ui2 on ui1.interes_id = ui2.interes_id
and ui1.usuar_id <> ui2.usuar_id
join Interesse i on ui1.interes_id = i.interes_id
join Usuario u2 on ui2.usuar_id = u2.usuar_id
where ui1.usuar_id = 1
order by u2.nome;

-- Conexões aceites de um usuário (id = 1)
select u1.nome as meu_nome, u2.nome as amigo, e.descricao as estado
from Connections c
inner join Connection_Estado ce on c.connect_id = ce.connect_id
inner join Estado e on ce.estado_id = e.estado_id
inner join Usuario u1 on c.usuar1_id = u1.usuar_id
inner join Usuario u2 on c.usuar2_id = u2.usuar_id
where (c.usuar1_id = 1 or c.usuar2_id = 1)
and e.descricao = 'aceite';

-- Posts de uma conversa (connect_id = 1)
select u.nome as autor, p.conteudo, p.data_envio
from Post p
inner join Usuario u on p.autor_id = u.usuar_id
where p.connect_id = 1
order by p.data_envio desc;

-- Lista de eventos e quem os criou
select e.titulo, e.local, e.data_evento, u.nome as criador
from Evento e
inner join Usuario u on e.criador_id = u.usuar_id;

-- Convites pendentes para o Miguel Duarte (id = 2)
select e.titulo as evento, u.nome as criador, st.descricao as estado
from Invite i
inner join Evento e on i.evento_id = e.evento_id
inner join Usuario u on e.criador_id = u.usuar_id
inner join Estado st on i.estado_id = st.estado_id
where i.convidado_id = 2
and st.descricao = 'pendente';

-- Posts de um evento (evento_id = 1)
select u.nome as autor, gp.conteudo, gp.data_envio
from Group_Post gp
inner join Usuario u on gp.autor_id = u.usuar_id
where gp.evento_id = 1
order by gp.data_envio desc;