# Moodly

**IADE**

**Engenharia Informática**



## Autores
- Arthur Hoffman (20241481)*
- Deuwer Rabelais (20241521)
- Isimar Quixito (20242038)
- Miguel Almeida (20240206)
- Vivandro Kambanza (20241805)

---

## Distribuição de Tarefas

| Tarefa | Arthur | Deuwer | Isimar | Miguel | Vivandro |
|------|-------|--------|--------|--------|----------|
| ReadMe inicial | 25% | 25% | 25% | 25% | — |
| Segundo ReadMe | 25% | 25% | 25% | 25% | — |
| ReadMe Final | 0% | 25% | 25% | 25% | 25% |
| Poster/Apresentação inicial | 10% | 10% | 10% | 70% | — |
| Segundo Poster/Apresentação | 40% | 40% | 10% | 10% | — |
| Poster/Apresentação Final | 0% | 60% | 10% | 20% | 10% |
| Base de dados | 0% | 85% | 5% | 5% | 5% |
| Servidor Spring Boot | 0% | 5% | 5% | 85% | 5% |
| Android Studio | 0% | 5% | 50% | 40% | 5% |
| Vídeo | 0% | 10% | 20% | 10% | 60% |

---

## O Moodly
O Moodly é uma aplicação mobile desenvolvida para facilitar a integração social de estudantes universitários, em especial calouros, que frequentemente enfrentam dificuldades em criar novas amizades no início da vida académica.
O problema identificado prende-se com o facto de muitos estudantes sentirem isolamento social, dificuldade em conhecer colegas com interesses semelhantes e pouca iniciativa para criar interações presenciais fora do contexto académico formal. As soluções existentes focam-se maioritariamente em relações românticas ou em comunidades genéricas, não respondendo diretamente à necessidade de criar amizades baseadas em interesses comuns no contexto universitário.
O Moodly procura resolver este problema ao permitir que os utilizadores descubram outras pessoas com base nos seus gostos culturais, estabeleçam conexões mútuas, comuniquem através de chat e organizem eventos sociais.


## Objetivos do Projeto
-	Criar uma aplicação móvel funcional que promova conexões sociais autênticas
-	Implementar um sistema de matching baseado em interesses
-	Desenvolver um backend REST robusto para gerir utilizadores, conexões, chats e eventos
-	Integrar frontend, backend e base de dados de forma consistente
-	Aplicar conhecimentos adquiridos nas diferentes Unidades Curriculares

## Público-Alvo
-	Estudantes universitários, especialmente do primeiro ano
-	Jovens adultos entre 18 e 30 anos
-	Pessoas que querem criar novas amizades
-	Pessoas que querem conhecer pessoas com interesses semelhantes
-	Pessoas que querem participar em eventos sociais informais
Embora o foco inicial seja o meio universitário, a aplicação pode futuramente ser adaptada a outros contextos sociais.


---

## Pesquisa de Mercado
**Tinder / Bumble**
-	Utilizam o conceito de swipe
-	Foco principal em relações românticas
-	Pouca valorização de interesses aprofundados

**Meetup**
-	Orientada para eventos e grupos
-	Menos centrada em relações individuais
-	Interface menos intuitiva para utilizadores jovens

**Discord**
-	Forte componente de chat e comunidades
-	Não possui sistema de descoberta pessoal direta


---

## Solução Implementada
Aplicação Android que permite:
-	Criar um perfil com interesses e foto
-	Descobrir outros utilizadores através de swipe
-	Criar conexões mútuas
-	Comunicar via chat individual ou em grupo
-	Criar e gerir eventos sociais


---

## Enquadramento nas Unidades Curriculares

### Programação de Dispositivos Móveis
-	Desenvolvimento da app Android em Kotlin
-	Utilização de Jetpack Compose
-	Implementação de navegação por abas
-	Gestão de estados, formulários e interação com API REST


### Programação Orientada a Objetos

-	Backend desenvolvido em Java com Spring Boot
-	Aplicação de conceitos OOP:
-	Encapsulamento
-	Separação de responsabilidades
-	Modelação de entidades (User, Connection, Event, Chat)
-	Implementação de uma API RESTful


### Bases de Dados
-	Utilização de base de dados relacional
-	Modelação de entidades e relações
-	Tabelas associativas
-	Persistência consistente de dados


---

## Requisitos Técnicos Finais
- Android Studio
- Kotlin / Java
- Jetpack Compose / Spring Boot
- MySQL
- HTTP/JSON
- GitHub

---

## Arquitetura da Solução
Cliente–Servidor com API REST
- Interface e experiência do utilizador
- Consumo da API REST
- Controllers REST
-	Serviços de negócio
- Repositórios de dados
-	Persistência de utilizadores, interesses, conexões, mensagens e eventos
Esta arquitetura garante escalabilidade, manutenção facilitada e separação de responsabilidades.


---

## Tecnologias Utilizadas

### Frontend
- Kotlin
- Jetpack Compose
- Retrofit
- Coil

### Backend
- Java
- Spring Boot
- Spring Data JPA

### Base de dados
-MySQL

### Outras
- GitHub(versionamento)
- Figma(mockups)
- ClickUp(Gestão de tarefas)
- WhatsApp(comunicação do grupo)

---

## Casos de Utilização
### Registo do utilizador
O utilizador pode criar uma nova conta escolhendo a opção de registo na aplicação. Durante este processo, são solicitadas informações básicas como nome, email e palavra-passe. Após o envio destes dados, o backend valida a informação e, caso o email ainda não esteja registado, cria um novo utilizador na base de dados. Se o registo for bem-sucedido, a aplicação inicia automaticamente a sessão do utilizador e encaminha-o para as etapas seguintes (escolher os interesses e a foto de perfil).
Caso o email já exista no sistema, o registo é recusado e o utilizador é informado do erro, sendo-lhe pedido que utilize um email diferente ou que faça login.

## Login 
O login permite ao utilizador aceder a uma conta já existente. Para isso, o utilizador introduz o seu email e palavra-passe. O backend valida as credenciais e, se estiverem corretas, devolve os dados do utilizador necessários para a sessão, como o identificador e a fotografia de perfil. Após a autenticação, o utilizador é direcionado para o ecrã principal da aplicação, onde passa a ter acesso à barra de navegação com todas as funcionalidades.
Se as credenciais estiverem incorretas, o acesso é negado e é apresentada uma mensagem de erro.

## Introdução de interesses

Depois do registo, o utilizador é encaminhado para uma tela onde pode definir os seus interesses pessoais, como música, séries ou jogos. Estes interesses são enviados para o backend e associados ao perfil do utilizador na base de dados. Os interesses passam a fazer parte do perfil e são utilizados para enriquecer a informação apresentada a outros utilizadores dentro da aplicação.
O utilizador pode editar os seus interesses posteriormente através da opção de edição de perfil.

## Escolha e Atualização da foto de perfil

O utilizador pode escolher uma fotografia de perfil a partir do seu dispositivo. A imagem é enviada para o servidor, onde é armazenada no sistema de ficheiros, sendo o respetivo caminho guardado na base de dados. A partir desse momento, a fotografia passa a ser exibida no perfil do utilizador e noutras áreas da aplicação onde a sua identidade visual é necessária, como listas de conexões e chats.
Sempre que o utilizador altera a fotografia, a imagem anterior é substituída e a nova passa a ser utilizada automaticamente.

## Perfil e Edição de perfil

Na aba de perfil, o utilizador pode visualizar a sua fotografia, os interesses definidos e o número de conexões existentes. Existe também um botão que permite editar o perfil, onde é possível alterar interesses ou atualizar a foto de perfil. 

## Descoberta de utilizadores

A funcionalidade de conectar permite ao utilizador descobrir novos perfis através de um sistema de scroll. A aplicação apresenta perfis de outros utilizadores que ainda não têm qualquer ligação com o utilizador atual. Perfis de utilizadores que já enviaram um pedido de conexão têm prioridade na lista apresentada, garantindo maior probabilidade de correspondência.
O utilizador pode ignorar um perfil ou demonstrar interesse ao deslizar para a direita, enviando assim um pedido de conexão.

Quando um utilizador demonstra interesse noutro perfil, é criado um pedido de conexão com estado pendente. Se o outro utilizador também demonstrar interesse, a conexão torna-se mútua automaticamente. A partir desse momento, ambos os utilizadores passam a constar na lista de conexões e ganham acesso a um chat individual privado.
Pedidos de conexão recebidos e ainda não respondidos podem ser visualizados na aba de conexões.

## Gestão de Conexões

Na aba de conexões, o utilizador pode ver todas as conexões mútuas existentes, bem como os pedidos de conexão pendentes. A partir desta lista, é possível aceitar ou rejeitar pedidos, bem como iniciar uma conversa individual com qualquer conexão já estabelecida.

## Chats Individuais

Cada conexão mútua cria automaticamente um canal de chat individual entre os dois utilizadores. Estes chats permitem a troca de mensagens de texto. O histórico de mensagens é guardado na base de dados, permitindo que as conversas sejam retomadas a qualquer momento.
A lista de chats apresenta as conversas ordenadas pela atividade mais recente.

## Eventos

Na aba de eventos, o utilizador pode visualizar todos os eventos relevantes, incluindo os que criou e aqueles para os quais foi convidado e aceitou. Cada evento é apresentado num cartão com informações resumidas, como o nome do evento, o organizador, a data e o número de participantes confirmados. Eventos cancelados serão vermelhos e podem ser apagados.

O utilizador pode aceder a uma vista detalhada que apresenta todas as informações adicionais do evento, como descrição completa, e localização. Ou também entrar no chat do evento. 

Cada evento possui um chat de grupo próprio, criado automaticamente no momento da criação do evento. Todos os utilizadores com participação confirmada podem trocar mensagens neste chat.

## Criação de eventos

Na aba de eventos, o utilizador pode criar um evento. O processo de criação começa com o preenchimento de informações como título, descrição, local e data do evento. Após a introdução destes dados, o utilizador avança para uma segunda etapa onde pode escolher os convidados a partir da sua lista de conexões mútuas.
Ao finalizar a criação, o evento é guardado na base de dados e é criado automaticamente um chat de grupo associado ao evento.

Depois de um evento ser criado, os convidados selecionados recebem um convite através de uma mensagem especial enviada no chat individual que partilham com o criador do evento.
O convite fica associado ao estado pendente até que o convidado tome uma decisão.

O utilizador convidado pode aceitar a participação no evento diretamente a partir do convite recebido no chat. Ao aceitar, o seu estado passa para confirmado e o evento passa a aparecer na aba de eventos do utilizador.

## Cancelament de eventos

Um utilizador convidado pode, a qualquer momento, cancelar a sua participação num evento previamente aceite. Esta ação atualiza o estado da participação,

O criador de um evento tem a possibilidade de o cancelar por completo. Quando isso acontece, todos os convites associados ao evento são marcados como cancelados e o chat de grupo deixa de estar ativo. Os participantes são informados do cancelamento através da interface da aplicação.

---

## Diagrama de Classes


<img width="6895" height="4580" alt="Social Network User-2025-12-14-162249" src="https://github.com/user-attachments/assets/f0a065f9-1563-4179-ab0e-8171b175983d" />


---

## Dicionário de Dados

## Tabela: usuario
Armazena os dados principais de cada utilizador.

| Campo | Tipo de Dados | Observação |
|------|---------------|------------|
| usuar_id | INT (PK) | Identificador único do utilizador |
| usuar_nome | VARCHAR(100) | Nome do utilizador (NOT NULL) |
| usuar_email | VARCHAR(150) | Email do utilizador (NOT NULL, UNIQUE) |
| usuar_senha_hash | VARCHAR(255) | Hash da palavra‑passe (NOT NULL) |
| usuar_foto_perfil | VARCHAR(255) | URL ou caminho da foto de perfil |

Cada utilizador pode possuir vários interesses, conexões, mensagens, eventos e convites.

---

## Tabela: interesse
Guarda categorias gerais de interesses.

| Campo | Tipo de Dados | Observação |
|------|---------------|------------|
| inter_id | INT (PK) | Identificador único do interesse |
| inter_nome | VARCHAR(60) | Nome da categoria do interesse (NOT NULL) |

Cada interesse pode possuir vários subinteresses.

---

## Tabela: subinteresse
Guarda interesses específicos dentro de uma categoria.

| Campo | Tipo de Dados | Observação |
|------|---------------|------------|
| subinter_id | INT (PK) | Identificador único do subinteresse |
| subinter_inter_id | INT (FK) | Interesse ao qual pertence (NOT NULL) |
| subinter_nome | VARCHAR(100) | Nome do subinteresse (NOT NULL) |

Um subinteresse pertence apenas a um interesse.

---

## Tabela: usuario_interesse
Tabela associativa entre utilizadores e subinteresses.

| Campo | Tipo de Dados | Observação |
|------|---------------|------------|
| usint_id | INT (PK) | Identificador da relação |
| usint_usuar_id | INT (FK) | Utilizador associado |
| usint_subinter_id | INT (FK) | Subinteresse associado |

---

## Tabela: connections
Representa conexões entre dois utilizadores.

| Campo | Tipo de Dados | Observação |
|------|---------------|------------|
| connect_id | INT (PK) | Identificador único da conexão |
| connect_usuar1_id | INT (FK) | Primeiro utilizador (NOT NULL) |
| connect_usuar2_id | INT (FK) | Segundo utilizador (NOT NULL) |
| connect_data_registo | DATETIME | Data de criação (DEFAULT CURRENT_TIMESTAMP) |

Cada conexão pode possuir vários estados.

---

## Tabela: estadoc
Define os estados possíveis de pedidos e conexões.

| Campo | Tipo de Dados | Observação |
|------|---------------|------------|
| estadoc_id | INT (PK) | Identificador do estado |
| estadoc_descricao | VARCHAR(60) | Descrição do estado (NOT NULL) |

Estado associado às conexões.

---

## Tabela: connection_estado
Histórico de estados de uma conexão.

| Campo | Tipo de Dados | Observação |
|------|---------------|------------|
| cnt_est_id | INT (PK) | Identificador do registo |
| cnt_est_connect_id | INT (FK) | Conexão associada (NOT NULL) |
| cnt_est_estado_id | INT (FK) | Estado atribuído (NOT NULL) |
| cnt_est_data_registo | DATETIME | Data do registo (DEFAULT CURRENT_TIMESTAMP) |

Tabela associativa entre connections e estadoc.

---

## Tabela: post
Mensagens privadas entre utilizadores conectados.

| Campo | Tipo de Dados | Observação |
|------|---------------|------------|
| post_id | INT (PK) | Identificador da mensagem |
| post_connect_id | INT (FK) | Conexão associada (NOT NULL) |
| post_usuar_id | INT (FK) | Utilizador que enviou |
| post_conteudo | TEXT | Conteúdo da mensagem (NOT NULL) |
| post_data_envio | DATETIME | Data de envio (DEFAULT CURRENT_TIMESTAMP) |

---

## Tabela: evento
Eventos criados por utilizadores.

| Campo | Tipo de Dados | Observação |
|------|---------------|------------|
| evento_id | INT (PK) | Identificador do evento |
| evento_usuar_id | INT (FK) | Criador do evento (NOT NULL) |
| evento_titulo | VARCHAR(150) | Título do evento (NOT NULL) |
| evento_descricao | TEXT | Descrição detalhada |
| evento_local | VARCHAR(200) | Local do evento |
| evento_data_evento | DATETIME | Data e hora do evento |

Um evento pode ter vários convites e mensagens de grupo.

---

## Tabela: invite
Convites enviados para eventos.

| Campo | Tipo de Dados | Observação |
|------|---------------|------------|
| invite_id | INT (PK) | Identificador do convite |
| invite_evento_id | INT (FK) | Evento associado (NOT NULL) |
| invite_usuar_id | INT (FK) | Utilizador convidado (NOT NULL) |
| invite_est_inv_id | INT (FK) | Estado do convite |
| invite_data_envio | DATETIME | Data de envio (DEFAULT CURRENT_TIMESTAMP) |

O criador do evento é implícito no próprio evento.

---

## Tabela: estado_invite
Define os estados possíveis de um convite.

| Campo | Tipo de Dados | Observação |
|------|---------------|------------|
| est_inv_id | INT (PK) | Identificador do estado |
| est_inv_nome | VARCHAR(60) | Nome do estado |

Um estado pode estar associado a vários convites.

---

## Tabela: group_post
Mensagens trocadas no chat de grupo de um evento.

| Campo | Tipo de Dados | Observação |
|------|---------------|------------|
| gp_id | INT (PK) | Identificador da mensagem |
| gp_evento_id | INT (FK) | Evento associado (NOT NULL) |
| gp_usuar_id | INT (FK) | Utilizador que enviou |
| gp_conteudo | TEXT | Conteúdo da mensagem (NOT NULL) |
| gp_data_envio | DATETIME | Data de envio (DEFAULT CURRENT_TIMESTAMP) |

Relaciona-se ao evento e aos utilizadores.

---

## Guia de Dados
Temos 7 utilizadores. Cada um com um nome, email, palavra-passe e foto de perfil registados no aplicativo. Os utilizadores são estudantes universitários com perfis distintos:
-	Danilo Fernandes (caloiro, Lic. Engenharia Informática);
-	Bernardo Alves (caloiro, Lic. Animação e Criação Visual);
-	Edson Lima (2º Ano, Lic. Ciências da Comunicação);
-	Isimar Quixito (3º Ano, Lic. Fotografia e Cultura Visual);
-	Miguel Almeida (2º Ano, Lic. Ciências da Comunicação);
-	Manuela Alexandre (1º Ano, Mestrado em Design e Produção de Jogos);
-	Carla Lwinny (1º Ano, Mestrado em Design e Publicidade).

Existem três categorias de interesses:
-	Filme/série;
-	Jogo;
-	Música.

E cada categoria tem um subconjunto enorme de interesses específicos que estão relacionadas com algum utilizador.

Interesses de Danilo Fernandes: 
Filmes/séries: It - A Coisa, Game of Thrones e Black Clover;
Jogos: The Legend of Zelda, Fortnite e Super Mario;
Músicas: Tory Lanez, Drake e Bryson Tiller.

Interesses de Bernardo Alves:
Filmes/séries: Bleach, The Incredibles e Scream;
Jogos: Hollow Knight, Call of Duty e The Legend of Zelda;
Músicas: CEF, Prodígio e Nelson Freitas.
 
Interesses de Edson Lima:
Filmes/séries: One Piece, Tower of God e Scary Movie;
Jogos: NBA, Minecraft e Gran Turismo;
Músicas: Michael Jackson, Drake e Queen.

Interesses de Isimar Quixito:
Filmes/séries: Stranger Things, Harry Potter e Senhor dos Anéis;
Jogos: FIFA, NBA e Call of Duty;
Músicas: Bryson Tiller, Landrick e MC Kevinho.

Interesses de Miguel Almeida:
Filmes/séries: Stranger Things, Missão Impossível e One Piece;
Jogos: Hollow Knight, Marvel Ultimate Alliance e FIFA;
Músicas: Queen, AC/DC e Tina Bell.

Interesses de Manuela Alexandre:
Filmes/séries: One Piece, Attack on Titan e Godzilla;
Jogos: FIFA, Silent Hill e Resident Evil;
Músicas: FatRat, Beyoncé e Olivia Rodrigo.

Interesses de Carla Lwinny:
Filmes/séries: Attack on Titan, Big Hero 6 e Crepúsculo;
Jogos: Cuphead, FIFA e The Legend of Zelda;
Músicas: Beyoncé, Doechii e Blackpink.


A partir do início do 1º Semestre as conexões começaram a ser feitas.
 O Danilo fez um pedido de conexão ao Bernardo, pelo fato de ambos gostarem do jogo “The Legend of Zelda”, e este aceitou o pedido no mesmo dia. Em seguida, Danilo fez outro pedido mas para o Isimar, no qual ficou pendente e não obteve resposta desde então.
Bernardo fez um pedido ao Miguel, em que este recusou três dias depois. Bernardo tentou também se conectar com a Carla, que também recusou no dia que o pedido foi feito.
Edson fez um pedido ao Danilo, que aceitou no mesmo dia porque gostam do mesmo artista, o Drake. Edson também se conectou ao Isimar, tendo Isimar aceitado dois dias depois (ambos gostam de NBA). O Edson tentou se conectar a Manuela, que o deixou com o pedido pendente.
Isimar fez um pedido de conexão ao Miguel, que aceitou no mesmo dia (gostam de Stranger Things e FIFA). E o Miguel fez um pedido ao Edson por gostarem de “One Piece”, que aceitou no dia seguinte.
Manuela pediu uma conexão ao Isimar e este aceitou no mesmo dia (gostam de FIFA). E a Manuela fez um pedido ao Miguel, que aceitou três dias depois (gostam de One Piece e FIFA).
E por último, a Carla se conectou à Manuela, na qual a Manuela aceitou no dia seguinte, por gostarem de Attack On Titan, Beyoncé e FIFA.

Em seguida, alguns partiram para uma conversa:
-	Danilo iniciou a sua conversa com o Bernardo dizendo o motivo do pedido e pergunta qual o jogo favorito da franquia que os dois gostam. Em seguida, Bernardo responde o seu e pergunta o mesmo para o Danilo e este também o responde. No final, Danilo pergunta ao Bernardo se sabia de um evento em vídeo exclusivo da mesma franquia;

-	Miguel questiona ao Edson sobre o estado de vida dele, na qual Edson lhe assegura, que está tudo certo, só está atarefado e o pergunta em que episódio de One Piece está. Miguel o responde que o entende e que está no episódio 1089, início de EggHead.

-	Manuela cumprimenta informalmente à Carla e pergunta se está tudo bem, posteriormente Carla responde e devolve a mesma pergunta. No final, Manuela destaca que percebeu que a Carla ama ouvir Beyoncé.
 
Foram registados dois eventos: um foi criado pelo Edson chamado de “Maratona de Stranger Things”, descrito como a “Estreia da nova e última temporada de Stranger Things “, que vai acontecer em Odivelas, no dia 27 de Novembro de 2025, às 18:00.

Neste evento, Edson convidou todos no mesmo dia, como o Danilo, que recusou o convite, e o Edson convidou o Isimar e o Miguel, onde ambos aceitaram.

Edson inicia a conversa em grupo, desabafando do tempo de espera entre a última temporada e a nova, e este convida os outros para irem à sua casa e promete comida para o evento. Isimar compartilha o mesmo sentimento e desabafa que já esqueçou do que aconteceu na temporada anterior. Miguel, entusiasmado, promete levar bebidas para não sobrecarregar o anfitrião do evento e o Edson agradece e promete enviar um resumo  para o Isimar se atualizar para a nova temporada.


O outro evento foi criado pela Manuela tem como título "Torneio de FIFA”, descrito como “Torneio Regional de FIFA DE Lisboa com prêmio, que acontece em Lisboa, na loja de jogos de vídeo, Gargula Gaming, no dia 6 de Dezembro de 2025, ás 16:00.

Neste evento, a Manuela convidou a Carla e o Isimar  no mesmo dia mas apenas a Carla aceitou o convite e o Isimar deixou pendente. No dia seguinte, Manuela convidou o Miguel que aceitou também.

Manuela inicia a conversa em grupo avisando sobre um torneio de FIFA e afirma que os convidados sabem jogar muito bem esse videojogo. Já Miguel agradece e questiona se o torneio é individual ou por equipas e a Carla responde sem certeza que é individual e que é difícil de acontecer por equipas. Miguel desiludido declara que deveria ser por equipas mas no final, a Manuela declara que foi confirmado recentemente que será por equipas.

__

## Documentação Rest

https://github.com/devHoff/Moodly/blob/787074a400a68b38c5e2cb956e76c93bd1ae88ba/documenta%C3%A7%C3%A3o/Terceira%20Entrega/Rest.md

---

## Manual do Utilizador

https://github.com/devHoff/Moodly/blob/787074a400a68b38c5e2cb956e76c93bd1ae88ba/documenta%C3%A7%C3%A3o/Terceira%20Entrega/Manual.md


---
## Grafico de Gantt

https://app.clickup.com/90151621011/v/g/2kyq94ck-195


---

## Considerações Finais


O desenvolvimento do moodly não foi tranquilo. Tivemos várias dificuldades especialmente na segunda entrega, mas tentamos corrigimos o nosso erro na terceira. A ausência de um dos membros do grupo afetou seriamente o processo de desenvolvimento do app, tornando algumas promessas iniciais impossíveis de ser entregues a tempo (integração com APIs, e sugestões de perfil com base nos interesses). No fim, apesar da app estar funcional, ele não concretizou muitos dos nossos desejos iniciais.

A estruturação do código, notavelmente no android studio pode não ter sido a melhor. Não separamos os componentes totalmente (como aprendemos ao longo da disciplina de Programação Mobile) e muitas das nossas telas não têm previews funcionais, visto que depois da ligação do backend, isto se tornou um difícil. Testes normalmente eram feitos simplesmente dando launch no app. Contudo o nosso projeto está pronto, e aprendemos muito.

