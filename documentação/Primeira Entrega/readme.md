# Moodly  
Elementos do Grupo: Arthur Hoffman, Deuwer Rabelais, Isimar Quixito, Miguel Almeida 

O **Moodly** é uma aplicação mobile pensada para ajudar calouros universitários a integrarem-se mais facilmente na vida académica. A ideia surge da facto de que muitos estudantes do primeiro ano têm dificuldade em encontrar novos amigos e criar amizades até mesmo dentro do seu curso. A aplicação funciona de forma semelhante a aplicativos como tinder, ou o Bumble, que popularizaram o conceito de “swipe” mas em vez de procurar relações românticas, o objetivo é encontrar pessoas com interesses em comum, como música, filmes ou jogos.  

## Guiões de Teste  

O João, calouro de Engenharia Informática, acabou de instalar o Moodly porque quer conhecer pessoas com gostos parecidos. Ao abrir a aba de Conectar, aparece-lhe o perfil da Ana, que adora música deftones. João desliza o perfil para a direita, interessado em fazer amizade. Mais tarde, a Ana também dá swipe em João. Ambos recebem uma notificação: houve um “connect”! A nova ligação fica automaticamente disponível na aba de Conexões.

Alguns dias depois, João abre a aba de Conexões e vê que já tem três "connects". Decide falar com a Ana, pois partilham o mesmo gosto por bandas alternativas. Ele abre o chat e envia a primeira mensagem: “Olá Ana, Qual é a tua musica favorita?”. Ana responde, animada por encontrar alguém com os mesmos interesses. Assim começa a conversa entre os dois. 

Passadas algumas semanas, João já tem um pequeno grupo de amigos através do Moodly. Como todos gostam de cinema, ele decide criar um Hangout para verem juntos a nova estreia de um filme. Na aba de Hangouts, define o evento com data, hora e local, e convida os colegas que já tinha adicionado. Cada convidado recebe o pedido de participação no telemóvel. À medida que vão aceitando, aparecem como confirmados e são automaticamente adicionados a um grupo de chat para organizarem detalhes da ida ao cinema.

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

## Modelo do Domínio (temporario)  

# Modelo de Domínio — Moodly

> **Moodly** é uma aplicação mobile que ajuda calouros universitários a se integrarem na vida acadêmica, conectando estudantes por afinidades — como música, filmes ou jogos — através de um sistema de *swipes* semelhante ao Tinder, mas com foco em **amizades e networking**.

---

## Visão Geral

O domínio do sistema representa os principais conceitos e relacionamentos do mundo real da aplicação — desde o estudante e o seu perfil até os *matches*, conversas e eventos universitários.

---

## Entidades Principais

### Estudante
Representa o utilizador da aplicação.  
**Atributos:**
- id
- nome
- emailInstitucional
- fotoUrl
- universidade
- faculdade
- curso
- ano
- dataNascimento
- bioCurta
- statusConta

---

### Perfil
Define as preferências e a visibilidade do estudante.  
**Atributos:**
- id
- estudanteId
- visibilidade
- preferenciasDescoberta
- objetivos
- horasAtivas

---

### Interesse
Catálogo de temas (música, filmes, jogos, etc.) usados para gerar afinidades.  
**Atributos:**
- id
- tipo
- nome
- metadados

---

### PerfilInteresse
Associação entre perfil e interesse.  
**Atributos:**
- perfilId
- interesseId
- nivelAfinidade
- ordemPreferencia

---

### Swipe
Ação de “gostar” ou “passar” em outro estudante.  
**Atributos:**
- id
- deEstudanteId
- paraEstudanteId
- direcao (like/pass)
- criadoEm

---

### Match
Criado quando dois estudantes dão *like* um no outro.  
**Atributos:**
- id
- estudanteAId
- estudanteBId
- afinidade
- criadoEm
- status

---

### Conversa
Canal de mensagens entre dois estudantes com *match*.  
**Atributos:**
- id
- matchId
- criadoEm
- ultimoEventoEm

---

### Mensagem
Mensagens trocadas dentro de uma conversa.  
**Atributos:**
- id
- conversaId
- autorId
- conteudo
- tipo
- enviadoEm
- lidoEm

---

### EventoCampus
Eventos e atividades universitárias sugeridas pela app.  
**Atributos:**
- id
- titulo
- descricao
- local
- dataHora
- tags
- organizador

---

### InscricaoEvento
Participação de um estudante em um evento.  
**Atributos:**
- id
- eventoId
- estudanteId
- status

---

### PreferenciaFiltro
Filtros que controlam o feed de *swipes*.  
**Atributos:**
- id
- estudanteId
- cursosAceites[]
- interessesAlvo[]
- distanciaKm
- idadeMin
- idadeMax
- idiomas[]

---

### Bloqueio
Registra estudantes bloqueados.  
**Atributos:**
- id
- deEstudanteId
- alvoEstudanteId
- criadoEm

---

### Sinalizacao
Reportes feitos por usuários para moderação.  
**Atributos:**
- id
- reporterId
- alvoEstudanteId
- motivo
- criadoEm
- estado

---

### Consentimento (RGPD)
Registo dos consentimentos do estudante.  
**Atributos:**
- id
- estudanteId
- termo
- versao
- aceiteEm
- origem

---

## Relacionamentos Principais

| Entidade | Relação | Multiplicidade | Descrição |
|-----------|----------|----------------|------------|
| Estudante → Perfil | 1 : 1 | Cada estudante tem um perfil | Relação direta entre estudante e perfil |
| Perfil ↔ Interesse | N : N (via PerfilInteresse) | Representa afinidades | Ligação entre perfis e interesses comuns |
| Estudante → Swipe | 1 : N | Ações de “like” ou “pass” | Cada estudante pode realizar vários swipes |
| Swipe ↔ Estudante (alvo) | N : 1 | Swipe é direcionado a outro estudante | Define quem recebeu o swipe |
| Match → Conversa | 1 : 1 | Cada match tem uma conversa | Cada emparelhamento cria uma conversa |
| Conversa → Mensagem | 1 : N | Mensagens trocadas | Cada conversa contém várias mensagens |
| Estudante ↔ EventoCampus | N : N (via InscricaoEvento) | Participações em eventos | Conecta estudantes aos eventos do campus |
| Estudante → PreferenciaFiltro | 1 : N | Configurações de descoberta | Define filtros de busca personalizados |
| Estudante → Bloqueio | 1 : N | Bloqueios efetuados | Registra usuários bloqueados |
| Estudante → Consentimento | 1 : N | Aceites de política e privacidade | Guarda os consentimentos RGPD do estudante |


---

## Diagrama

```mermaid
classDiagram
direction LR

class Estudante {
  +id
  +nome
  +emailInstitucional
  +fotoUrl
  +universidade
  +faculdade
  +curso
  +ano
  +dataNascimento
  +bioCurta
  +statusConta
}

class Perfil {
  +id
  +estudanteId
  +visibilidade
  +preferenciasDescoberta
  +objetivos
  +horasAtivas
}

class Interesse {
  +id
  +tipo
  +nome
  +metadados
}

class PerfilInteresse {
  +perfilId
  +interesseId
  +nivelAfinidade
  +ordemPreferencia
}

class PreferenciaFiltro {
  +id
  +estudanteId
  +cursosAceites
  +interessesAlvo
  +distanciaKm
  +idadeMin
  +idadeMax
  +idiomas
}

class Swipe {
  +id
  +deEstudanteId
  +paraEstudanteId
  +direcao
  +criadoEm
}

class Match {
  +id
  +estudanteAId
  +estudanteBId
  +afinidade
  +criadoEm
  +status
}

class Conversa {
  +id
  +matchId
  +criadoEm
  +ultimoEventoEm
}

class Mensagem {
  +id
  +conversaId
  +autorId
  +conteudo
  +tipo
  +enviadoEm
  +lidoEm
}

class EventoCampus {
  +id
  +titulo
  +descricao
  +local
  +dataHora
  +tags
  +organizador
}

class InscricaoEvento {
  +id
  +eventoId
  +estudanteId
  +status
}

class Sinalizacao {
  +id
  +reporterId
  +alvoEstudanteId
  +alvoMensagemId
  +motivo
  +estado
  +criadoEm
}

class Bloqueio {
  +id
  +deEstudanteId
  +alvoEstudanteId
  +criadoEm
}

class Consentimento {
  +id
  +estudanteId
  +termo
  +versao
  +aceiteEm
  +origem
}

Estudante --> Perfil
Perfil --> PerfilInteresse
PerfilInteresse --> Interesse
Estudante --> Swipe
Swipe --> Estudante
Estudante --> Match
Match --> Conversa
Conversa --> Mensagem
Estudante --> Mensagem
Estudante --> PreferenciaFiltro
EventoCampus --> InscricaoEvento
Estudante --> InscricaoEvento
Estudante --> Sinalizacao
Estudante --> Bloqueio
Estudante --> Consentimento





