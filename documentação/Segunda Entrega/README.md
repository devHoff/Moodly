# Moodly  
Elementos do Grupo: Arthur Hoffman, Deuwer Rabelais, Isimar Quixito, Miguel Almeida 

O **Moodly** é uma aplicação mobile pensada para ajudar calouros universitários a integrarem-se mais facilmente na vida académica. A ideia surge da facto de que muitos estudantes do primeiro ano têm dificuldade em encontrar novos amigos e criar amizades até mesmo dentro do seu curso. A aplicação funciona de forma semelhante a aplicativos como tinder, ou o Bumble, que popularizaram o conceito de “swipe” mas em vez de procurar relações românticas, o objetivo é encontrar pessoas com interesses em comum, como música, filmes ou jogos.  

## Guiões de Teste  

O João, calouro de Engenharia Informática, acabou de instalar o Moodly porque quer conhecer pessoas com gostos parecidos. Ao abrir a aba de Conectar, aparece-lhe o perfil da Ana, que adora música deftones. João desliza o perfil para a direita, interessado em fazer amizade. Mais tarde, a Ana também dá swipe em João. Ambos recebem uma notificação: houve um “connect”! A nova ligação fica automaticamente disponível na aba de Conexões.

Alguns dias depois, João abre a aba de Conexões e vê que já tem três "connects". Decide falar com a Ana, pois partilham o mesmo gosto por bandas alternativas. Ele abre o chat e envia a primeira mensagem: “Olá Ana, Qual é a tua musica favorita?”. Ana responde, animada por encontrar alguém com os mesmos interesses. Assim começa a conversa entre os dois. 

Passadas algumas semanas, João já tem um pequeno grupo de amigos através do Moodly. Como todos gostam de cinema, ele decide criar um Hangout para verem juntos a nova estreia de um filme. Na aba de Hangouts, define o evento com data, hora e local, e convida os colegas que já tinha adicionado. Cada convidado recebe o pedido de participação no telemóvel. À medida que vão aceitando, aparecem como confirmados e são automaticamente adicionados a um grupo de chat para organizarem detalhes da ida ao cinema.

## Personas

<img width="1920" height="1080" alt="Manual da persona apresentação" src="https://github.com/user-attachments/assets/24383d5e-da09-47b3-91e5-5abfae72673d" />

<img width="1920" height="1080" alt="Manual da persona apresentação-2" src="https://github.com/user-attachments/assets/367eb629-ee26-4f45-99fd-ad243c082d58" />



## Plano de Trabalhos  
Fase 1 – Planeamento e Pesquisa
Objetivos:
Refinar a ideia da aplicação.
Fazer pesquisa de mercado: analisar apps como Tinder, Bumble, Meetup, Discord.
Levantar requisitos funcionais e não funcionais com mais detalhe.

Fase 2 – Design e Modelagem 
Objetivos:
Criar mockups da interface (Figma)
Definir o modelo do domínio (entidades: User, Connection, Chat, Hangout).
Criar diagrama de casos de uso.

Fase 3 – Desenvolvimento Backend
Objetivos:
Criar o projeto Spring Boot.
Implementar API REST (endpoints: autenticação, perfis, conexões, chat, hangouts).
Configurar base de dados relacional (PostgreSQL ou MySQL).

Fase 4 – Desenvolvimento Frontend 
Objetivos:
Criar app Android em Kotlin + Jetpack Compose.
Implementar:
Tela de login/registro.
Tela de swipe (“Conectar”).
Tela de conexões e chat.
Tela de criação/gestão de Hangouts.

Fase 5 – Testes e Validação 
Objetivos:
Aplicar os guiões de teste definidos (swipe, conexões, mensagens, hangouts).
Testes de usabilidade com colegas (feedback da interface).

Fase 6 – Entrega e Apresentação
Objetivos:
Preparar a apresentação final do projeto.
Redigir relatório/documentação formal (incluindo todos os artefatos).



## Project Charter e WBS  

Justificação / Enquadramento
Num contexto em que as aplicações de encontros se tornaram populares, muitas focam apenas em aspetos superficiais.  
A **Moodly** diferencia-se por colocar em primeiro plano os **interesses culturais dos utilizadores**, permitindo que novas amizades ou relações surjam de forma natural através da partilha de música, livros ou jogos preferidos.  

Objetivos
- Criar uma aplicação móvel que sugere matches entre utilizadores com base nos seus gostos culturais.  
- Implementar um sistema de perfis de utilizador, com interesses armazenados em base de dados relacional.  
- Desenvolver um backend em **Spring Boot** com API REST para gerir os dados dos utilizadores e matches.  
- Criar mockups e interfaces no **Figma** com foco em usabilidade.  
- Garantir a integração completa entre **App (Kotlin), Backend (Java/Spring) e BD (PostgreSQL)**.  
- Apresentar o projeto com relatório, poster e apresentação.  

Escopo
**Incluído no projeto:**  
- Criação de perfis de utilizador.  
- Sistema de preferências culturais (música, literatura, jogos).  
- Algoritmo de matching baseado em interesses.  
- Interface de sugestões de matches e mensagens básicas.  
- Integração com backend REST e base de dados.  
- Mockups em Figma e documentação no GitHub.  

Público-Alvo
- Jovens adultos (18–30 anos) que procuram **novas amizades ou relações**.  
- Estudantes universitários e utilizadores que valorizam **gostos culturais** como música, livros ou jogos.  

Stakeholders
- **Equipa de Desenvolvimento (Alunos):** responsáveis pela implementação.  
- **Docentes:** supervisão e validação académica.  
- **Utilizadores finais:** jovens interessados em conhecer pessoas através de interesses culturais.  

Requisitos de Alto Nível
- Linguagens: **Kotlin (Frontend), Java (Backend)**.  
- Frameworks: **Spring Boot, Jetpack Compose**.  
- BD: **PostgreSQL/MySQL**.  
- Arquitetura: **MVC + REST**.  
- Versionamento: **GitHub**.  
- Mockups: **Figma**.  
- Gestão de tarefas: **ClickUp**.  
- Comunicação: **Discord**.  

Riscos e Desafios
- **Gestão do tempo:** risco de atrasos na entrega.  
- **Complexidade técnica:** desenvolvimento do sistema de matching.  
- **Coordenação:** necessidade de alinhamento constante da equipa.  
- **Mudança de requisitos:** adaptação às revisões dos docentes.
  
Cronograma de Alto Nível
- **Semana 1–4:** Proposta inicial + Mockups + Relatório v1 + Poster + Vídeo.  
- **Semana 5–10:** Protótipo funcional (login, perfis, BD conectada, primeiros matches).  
- **Semana 11–14:** Implementação do matching e chat básico.  
- **Semana 15:** Entrega final (App no dispositivo, relatório completo, apresentação).
  
Recursos Necessários
- **Hardware:** computadores, smartphones Android.  
- **Software:** Android Studio, Spring Boot, PostgreSQL, GitHub, Figma, ClickUp, Discord.  
- **Recursos Humanos:** equipa de 2–3 alunos.  

Critérios de Sucesso
- App funcional com sistema de perfis e matching.  
- Base de dados relacional corretamente integrada.  
- Backend REST funcional com documentação.  
- Mockups e interfaces aprovados.  
- Entrega final completa com relatório, vídeo e apresentação.  

---

## Requisitos Funcionais  
Estes requisitos descrevem as funcionalidades que a aplicação deve obrigatoriamente oferecer:  

- **Swipe de Perfis**: o utilizador deve poder visualizar perfis de outros estudantes e deslizar para a direita ou esquerda, indicando interesse em conectar-se ou não.  
- **Gestão de Conexões (Matches)**: sempre que dois utilizadores mostrem interesse mútuo, deve ser criada uma conexão visível na aba de Conexões.  
- **Chats Individuais**: a aplicação permitirá iniciar conversas privadas com base nas conexões estabelecidas.  
- **Group Chats**: deve ser possível criar conversas em grupo, associadas a hangouts.  
- **Criação e Participação em Hangouts**: os utilizadores devem poder criar eventos com data, hora e local, convidar conexões, e gerir confirmações. Os participantes confirmados entram automaticamente num chat de grupo do evento.  
- **Perfil do Utilizador**: cada utilizador deve ter um perfil editável, incluindo nome, curso, interesses (música, filmes, jogos), foto e definições básicas.  

---

## Requisitos Não Funcionais  
Estes requisitos garantem a qualidade, segurança e usabilidade da aplicação:  

- **Interface Intuitiva e Amigável**: o design vai ser simples, moderno e adaptado ao público, facilitando a navegação.  
- **Qualidade de Vida (QoL Features)**: funcionalidades pequenas, mas importantes, como notificações claras, opção de silenciar chats, e confirmação visual de ações (por exemplo, quando se envia uma mensagem).   
- **Desempenho e Escalabilidade**: a aplicação vai suportar pelo menos 50 utilizadores em simultâneo sem perdas de desempenho significativas.  
- **Persistência de Dados**: todas as informações (perfis, conexões, mensagens, hangouts) devem ser guardadas de forma consistente numa base de dados relacional MySQL.  
- **Disponibilidade e Fiabilidade**: a aplicação vai estar disponível pelo menos 95% do tempo, minimizando falhas e interrupções nos serviços.  

---

## Diagrama de Classes 

<img width="868" height="321" alt="MER_Moodly drawio-3" src="https://github.com/user-attachments/assets/22b99901-6d72-45eb-a79e-25a2bffe47dd" />

---

## Dicionário de Dados

### Usuário

Armazena os dados principais de cada utilizador do Moodly.

**Campos** - **Tipos de Dados**

* usuar_id   - int auto_increment primary key;
* nome       - varchar(100) not null;
* foto_perfil - varchar(255).


Cada usuário pode ter vários interesses, conexões, posts, eventos e convites.

--

### Interesse

Guarda os diferentes interesses possíveis, categorizados por tipo.

**Campos** - **Tipos de Dados**

* interes_id - int auto_increment primary key
* tipo       - enum('musica','filme','jogo','outro') not null
* nome       - varchar(150) not null

Relaciona-se com Usuario através da tabela associativa Usuario_Interesse

--

### Usuario_Interesse

Tabela associativa que liga utilizadores aos seus interesses.

**Campos** - **Tipos de Dados**
* usuar_interes_id - int auto_increment primary key
* usuar_id   - int not null (foreign key para  Usuario(usuar_id)
* interes_id - int not null (foreign key para Interesse(interes_id)

-- 

### Estado

Define o estado de pedidos, convites e conexões

**Campos** - **Tipos de Dados**
* estado_id  - int auto_increment primary key
* descricao  - enum('pendente','aceite','recusado','bloqueado','espera') not null

Usado tanto em Connections quanto em Invite.

--

### Connections

Representa as conexões entre dois utilizadores (quando ambos se aceitam).

**Campos** - **Tipos de Dados**
* connect_id - int auto_increment primary key
* usuar1_id  - int not null (foreign key para Usuario(usuar_id))
* usuar2_id  - int not null (foreign key para Usuario(usuar_id))
* data_registo - datetime default current_timestamp

Ligação entre dois utilizadores. Cada conexão pode ter vários estados.

--

### Connection_Estado

Regista o histórico de estados de uma conexão

**Campos** - **Tipos de Dados**
* connect_estado_id - int auto_increment primary key
* connect_id - int not null (foreign key para Connections(connect_id))
* estado_id  - int not null (foreign key para Estado(estado_id))
* data_registo - datetime default current_timestamp

--

### Post

Mensagens privadas trocadas entre usuários que têm uma conexão.

**Campos** - **Tipos de Dados**
* post_id    - int auto_increment primary key
* connect_id - int not null (foreign key para Connections(connect_id))
* autor_id   - int not null (foreign key para Usuario(usuar_id))
* conteudo   - text not null
* data_envio - datetime default current_timestamp

--

### Evento

Eventos criados por utilizadores

**Campos** - **Tipos de Dados**
* evento_id  - int auto_increment primary key
* criador_id - int not null (foreign key para Usuario(usuar_id))
* titulo     - varchar(150) not null
* descricao  - text
* local      - varchar(200)
* data_evento - datetime

--

### Invite

Convites enviados para usuários conectados participarem de um evento

**Campos** - **Tipos de Dados**
* invite_id  - int auto_increment primary key
* evento_id  - int not null (foreign key para Evento(evento_id))
* convidado_id - int not null (foreign key para Usuario(usuar_id)
* estado_id  - int (foreign key para Estado(estado_id))
* data_envio - datetime default current_timestamp

O criador do evento é implícito, por isso não é necssário campo "criador_id"

--

### Group_Post

Mensagens trocadas dentro do grupo do evento (chat coletivo)

**Campos** - **Tipos de Dados**
* group_post_id - int auto_increment primary key
* evento_id  - int not null (foreign key para Evento(evento_id))
* autor_id   - int not null (foreign key para Usuario(usuar_id))
* conteudo   - text not null
* data_envio - datetime default current_timestamp

---


## Planificação

https://sharing.clickup.com/90151621011/l/h/6-901516711608-1/d48b485a365500a

## Poster
<img width="186" height="400" alt="Frame 1 (1)" src="https://github.com/user-attachments/assets/bf8fb3de-2057-4767-ae78-b80f8e2b38e4" />

## Conclusão
OMoodly surge como uma solução inovadora para facilitar a integração social de calouros universitários, promovendo conexões baseadas em interesses em comum e fortalecendo o sentimento de pertença no ambiente académico.

A aplicação demonstra o potencial da tecnologia para melhorar a experiência estudantil, reduzindo a solidão e incentivando a interação entre colegas.
Futuramente, pretende-se aprimorar o sistema de recomendações, implementar chat em tempo real e expandir as funcionalidades para diferentes instituições e contextos sociais.
O Moodly não é apenas uma aplicação — é uma ponte entre pessoas.

Cada “match” representa uma nova amizade, uma nova história e uma experiência universitária mais humana.
O futuro do Moodly é continuar a aproximar estudantes, tornando o início da vida académica uma jornada mais acolhedora e conectada.


