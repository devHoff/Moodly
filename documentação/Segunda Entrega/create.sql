

-- USUARIO
create table Usuario (
usuar_id int auto_increment primary key,
nome varchar(100) not null,
foto_perfil varchar(255)
);

-- INTERESSE
create table Interesse (
interes_id int auto_increment primary key,
tipo enum('musica','filme','jogo','outro') not null,
nome varchar(150) not null
);

-- USUARIO_INTERESSE
create table Usuario_Interesse (
usuar_interes_id int auto_increment primary key,
usuar_id int not null,
interes_id int not null,
foreign key (usuar_id) references Usuario(usuar_id),
foreign key (interes_id) references Interesse(interes_id)
);

-- ESTADO
create table Estado (
estado_id int auto_increment primary key,
descricao enum('pendente','aceite','recusado','bloqueado','espera') not null
);

-- CONNECTIONS
create table Connections (
connect_id int auto_increment primary key,
usuar1_id int not null,
usuar2_id int not null,
data_criacao datetime default current_timestamp,
foreign key (usuar1_id) references Usuario(usuar_id),
foreign key (usuar2_id) references Usuario(usuar_id)
);

-- CONNECTION_ESTADO
create table Connection_Estado (
connect_estado_id int auto_increment primary key,
connect_id int not null,
estado_id int not null,
data_registo datetime default current_timestamp,
foreign key (connect_id) references Connections(connect_id),
foreign key (estado_id) references Estado(estado_id)
);

-- POST
create table Post (
post_id int auto_increment primary key,
connect_id int not null,
autor_id int not null,
conteudo text not null,
data_envio datetime default current_timestamp,
foreign key (connect_id) references Connections(connect_id),
foreign key (autor_id) references Usuario(usuar_id)
);

-- EVENTO
create table Evento (
evento_id int auto_increment primary key,
criador_id int not null,
titulo varchar(150) not null,
descricao text,
local varchar(200),
data_evento datetime,
foreign key (criador_id) references Usuario(usuar_id)
);

-- INVITE
create table Invite (
invite_id int auto_increment primary key,
evento_id int not null,
convidado_id int not null,
estado_id int,
data_envio datetime default current_timestamp,
foreign key (evento_id) references Evento(evento_id),
foreign key (convidado_id) references Usuario(usuar_id),
foreign key (estado_id) references Estado(estado_id)
);

-- Group_Post 
create table Group_Post(
group_post_id int auto_increment primary key,
evento_id int not null,
autor_id int not null,
conteudo text not null,
data_envio datetime default current_timestamp,
foreign key (evento_id) references Evento(evento_id),
foreign key (autor_id) references Usuario(usuar_id)
);



