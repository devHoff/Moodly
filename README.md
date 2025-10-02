## Moodly  
Elementos do Grupo:  

O **Moodly** é uma aplicação mobile pensada para ajudar calouros universitários a integrarem-se mais facilmente na vida académica. A ideia surge da facto de que muitos estudantes do primeiro ano têm dificuldade em encontrar novos amigos e criar amizades até mesmo dentro do seu curso. A aplicação funciona de forma semelhante a aplicativos como tinder, ou o Bumble, que popularizaram o conceito de “swipe” mas em vez de procurar relações românticas, o objetivo é encontrar pessoas com interesses em comum, como música, filmes ou livros.  

## Guiões de Teste  

O João, calouro de Engenharia Informática, acabou de instalar o Moodly porque quer conhecer pessoas com gostos parecidos. Ao abrir a aba de Conectar, aparece-lhe o perfil da Ana, que adora música deftones. João desliza o perfil para a direita, interessado em fazer amizade. Mais tarde, a Ana também dá swipe em João. Ambos recebem uma notificação: houve um “connect”! A nova ligação fica automaticamente disponível na aba de Conexões.

Alguns dias depois, João abre a aba de Conexões e vê que já tem três "connects". Decide falar com a Ana, pois partilham o mesmo gosto por bandas alternativas. Ele abre o chat e envia a primeira mensagem: “Olá Ana, Qual é a tua musica favorita?”. Ana responde, animada por encontrar alguém com os mesmos interesses. Assim começa a conversa entre os dois. 

Passadas algumas semanas, João já tem um pequeno grupo de amigos através do Moodly. Como todos gostam de cinema, ele decide criar um Hangout para verem juntos a nova estreia de u filme. Na aba de Hangouts, define o evento com data, hora e local, e convida os colegas que já tinha adicionado. Cada convidado recebe o pedido de participação no telemóvel. À medida que vão aceitando, aparecem como confirmados e são automaticamente adicionados a um grupo de chat para organizarem detalhes da ida ao cinema.

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
Testes unitários (JUnit no backend, Espresso no Android).
Testes de usabilidade com colegas (feedback da interface).

Fase 6 – Entrega e Apresentação
Objetivos:
Preparar a apresentação final do projeto.
Redigir relatório/documentação formal (incluindo todos os artefatos).



## Project Charter e WBS  

(O trabalho será dividido em várias frentes. Primeiro, o planeamento, que abrange a definição da ideia, pesquisa de mercado e levantamento de requisitos. Depois, a parte de design, que inclui mockups e modelo do domínio. Segue-se o desenvolvimento, com o backend em Spring Boot e a integração de uma base de dados relacional, enquanto o frontend será implementado em Android Studio com Kotlin e Jetpack Compose. Finalmente, avançamos para a fase de testes, ajustando as funcionalidades de acordo com os guiões de teste, antes de preparar a entrega formal.) 

---

## Requisitos Funcionais  
Estes requisitos descrevem as funcionalidades que a aplicação deve obrigatoriamente oferecer:  

- **Swipe de Perfis**: o utilizador deve poder visualizar perfis de outros estudantes e deslizar para a direita ou esquerda, indicando interesse em conectar-se ou não.  
- **Gestão de Conexões (Matches)**: sempre que dois utilizadores mostrem interesse mútuo, deve ser criada uma conexão visível na aba de Conexões.  
- **Chats Individuais**: a aplicação permitirá iniciar conversas privadas com base nas conexões estabelecidas.  
- **Group Chats**: deve ser possível criar conversas em grupo, associadas a hangouts.  
- **Criação e Participação em Hangouts**: os utilizadores devem poder criar eventos com data, hora e local, convidar conexões, e gerir confirmações. Os participantes confirmados entram automaticamente num chat de grupo do evento.  
- **Perfil do Utilizador**: cada utilizador deve ter um perfil editável, incluindo nome, curso, interesses (música, filmes, livros), foto e definições básicas.  

---

## Requisitos Não Funcionais  
Estes requisitos garantem a qualidade, segurança e usabilidade da aplicação:  

- **Interface Intuitiva e Amigável**: o design vai ser simples, moderno e adaptado ao público, facilitando a navegação.  
- **Qualidade de Vida (QoL Features)**: funcionalidades pequenas, mas importantes, como notificações claras, opção de silenciar chats, e confirmação visual de ações (por exemplo, quando se envia uma mensagem).   
- **Desempenho e Escalabilidade**: a aplicação vai suportar pelo menos 50 utilizadores em simultâneo sem perdas de desempenho significativas.  
- **Persistência de Dados**: todas as informações (perfis, conexões, mensagens, hangouts) devem ser guardadas de forma consistente numa base de dados relacional (PostgreSQL ou MySQL).  
- **Disponibilidade e Fiabilidade**: a aplicação vai estar disponível pelo menos 95% do tempo, minimizando falhas e interrupções nos serviços.  

---

## Modelo do Domínio (temporario)  

(O modelo do domínio inclui entidades como **User**, responsável por armazenar os dados do utilizador; **Connection**, que regista as ligações estabelecidas; **Chat**, que organiza as conversas; e **Hangout**, que representa os encontros criados e os participantes confirmados. Estas entidades e as suas relações serão detalhadas em versões posteriores, incluindo diagramas mais completos)  

---

## 10. Mockups e Interfaces  

...

## 11. Planificação (temporario)

(gráfico de Gantt construído em ClickUp. Nele devem estar assinaladas as principais fases do projeto, desde a definição da ideia, passando pelo design e desenvolvimento, até à entrega do relatório e do poster. Cada etapa deve estar distribuída pelas semanas, de modo a garantir o cumprimento dos prazos definidos no briefing.) 


