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

Deixados para a segunda entrega...


---

## Mockups e Interfaces  
<img width="186" height="400" alt="Frame 1" src="https://github.com/user-attachments/assets/0877e7e8-7af3-4c9d-9b77-e8036ad5fbbc" /> <img width="186" height="400" alt="Frame 2" src="https://github.com/user-attachments/assets/a01359f8-e740-40fe-b9a4-d385c3126405" /> <img width="186" height="400" alt="Frame 3" src="https://github.com/user-attachments/assets/906aec40-1421-4739-b0e3-34719f70a6e5" /> <img width="186" height="400" alt="Frame 4" src="https://github.com/user-attachments/assets/81c9b3fe-2540-4e37-bc95-74c0523eba77" /> <img width="186" height="400" alt="Frame 5" src="https://github.com/user-attachments/assets/bcc3224e-6ece-4b04-8214-e4c8f5ed8a07" /> <img width="186" height="400" alt="Frame 6" src="https://github.com/user-attachments/assets/5cb317f3-c848-47fc-aa8a-fb84507710cc" />

## Planificação

https://sharing.clickup.com/90151621011/g/h/2kyq94ck-195/f3fab03e7f271ec

## Poster
<img width="186" height="400" alt="Frame 1 (1)" src="https://github.com/user-attachments/assets/bf8fb3de-2057-4767-ae78-b80f8e2b38e4" />

## Powerpoint (em png)

<img width="4000" height="2250" alt="Apresentação-1" src="https://github.com/user-attachments/assets/884ae999-702e-4817-a8aa-f5f2fea43871" />
<img width="4000" height="2250" alt="Apresentação-2" src="https://github.com/user-attachments/assets/3c4755d1-134a-4907-a29a-5518b51603fc" />
<img width="4000" height="2250" alt="Apresentação-3" src="https://github.com/user-attachments/assets/ebd09e0e-48a5-46a3-b737-89fb4c120b2a" />
<img width="4000" height="2250" alt="Apresentação-4" src="https://github.com/user-attachments/assets/20aea681-56ab-4c5d-bda8-1484eb76c965" />
<img width="4000" height="2250" alt="Apresentação-5" src="https://github.com/user-attachments/assets/17f0e7dd-12d6-4800-929f-d854b5379e3a" />
<img width="4000" height="2250" alt="Apresentação-6" src="https://github.com/user-attachments/assets/160584a9-3746-48cd-a997-156d9a5de3ea" />
<img width="4000" height="2250" alt="Apresentação-7" src="https://github.com/user-attachments/assets/61e21061-239f-4c2d-9b49-484a5e72a470" />
<img width="4000" height="2250" alt="Apresentação-8" src="https://github.com/user-attachments/assets/3a18ab45-ed02-4c71-bc5e-8c3eef2fef2f" />


