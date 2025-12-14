# Moodly Rest

---

## Servidor Online

- **URL:** `/`
- **Method:** `GET`
- **Success Response:** `200 OK`
- **Descrição:** Endpoint de teste para verificar se o servidor está online.
```json
curl -X GET http://localhost:8080/
```
---

## Autenticação

### Criar conta

- **URL:** `/api/auth/signup`
- **Method:** `POST`

#### Data Params (JSON)
```json
{
  "nome": "joana",
  "email": "joano@email.com",
  "password": "123456"
}
```

#### Success Response
- `200 OK`

```json
{ 
"id": 1, 
"nome": "Joana",
 "email": "joana@email.com", 
"fotoPerfil": null
 }
```

#### Error Response
- `400 Bad Request` → `"Dados inválidos"`
- `409 Conflict` → `"Email já registado"`

---

### Autenticar utilizador

- **URL:** `/api/auth/login`
- **Method:** `POST`

#### Data Params (JSON)
```json
{
  "email": "joana@email.com",
  "password": "123456"
}
```

#### Success Response
- `200 OK`

```json
"id": 1, 
"nome": "Joana", 
"email": "joana@email.com",
 "fotoPerfil": "/uploads/profile/..."
```
{

#### Error Responses
- `400 Bad Request` → `"Dados inválidos"`
- `401 Unauthorized` → `"Credenciais inválidas"`

---

## Utilizadores

### Obter utilizador

- **URL:** `/api/usuarios/{id}`
- **Method:** `GET`

#### URL Params
- `id` (integer) — obrigatório

#### Success Response
- `200 OK`

#### Error Response
- `404 Not Found`

---

### Atualizar dados do utilizador

- **URL:** `/api/usuarios/{id}`
- **Method:** `PUT`

#### Data Params (JSON)
Campos opcionais do utilizador.
```json
{
  "fotoPerfil": "user-2.png",
  "interesses": [
    { "tipo": "musica", "nome": "Pop" },
    { "tipo": "filme",  "nome": "Drama" }
  ]
}
```


#### Success Response
- `200 OK` (mesma estrutura do GET)

#### Error Response
- `404 Not Found`

---

### Obter perfil e interesses

- **URL:** `/api/profile/{userId}`
- **Method:** `GET`

#### URL Params
- `userId` (integer) — obrigatório

#### Success Response
- `200 OK`

```json
{
  "id": 1,
  "nome": "Joana",
  "email": "joana@email.com",
  "fotoPerfil": "/uploads/profile/...",
  "interesses": [{ "tipo": "musica", "nome": "Drake" }]
}
```
#### Error Response
- `500 Internal Server Error` → `"User not found"`

---

## Conexões

### Pedir conexão

- **URL:** `/api/connections/request`
- **Method:** `POST`

#### Data Params (JSON)
```json
{
  "fromUserId": 1,
  "toUserId": 2
}
```

#### Success Response
- `200 OK`

```json
{ 
"connectionId": 10, 
"status": "aceite",
 "mutual": true
}
```
- Se for mútuo, a conexão é criada automaticamente

#### Error Responses
- `400 Bad Request` → `"IDs inválidos"`
- `404 Not Found` → `"User não encontrado"`
- `500 Internal Server Error`

---

### Listar conexões mútuas

- **URL:** `/api/connections/mutual/{userId}`
- **Method:** `GET`

#### Success Response
- `200 OK` → Lista de utilizadores

---

### Pedidos pendentes recebidos

- **URL:** `/api/connections/pending/{userId}`
- **Method:** `GET`

#### Success Response
- `200 OK`

```json
{
 "connectionId": 10,
 "userId": 1, 
"nome": "Joana", 
"fotoPerfil": "/uploads/profile/..." 
}
```

---

### Pedidos enviados 

- **URL:** `/api/connections/outgoing/{userId}`
- **Method:** `GET`

#### Success Response
- `200 OK`
```json
{
 "connectionId": 10,
 "userId": 2, 
"nome": "Rui", 
"fotoPerfil": null, 
"mutual": false 
}
```
---

### Descobrir utilizadores

- **URL:** `/api/connections/discover/{userId}`
- **Method:** `GET`

#### Query Params
- `limit` (integer, opcional — default 20)

#### Success Response
- `200 OK` → Lista de utilizadores
```json
curl -X GET "http://localhost:8080/api/connections/discover/1?limit=20"
```
---
## Eventos

### Criar evento + enviar convites

- **URL:** `/api/events`
- **Method:** `POST`

#### Data Params (JSON)
```json
{
  "criadorId:1
  "titulo": "string",
  "descricao": "string",
  "local": "string",
  "dataEvento": "datetime",
  "convidadosIds": [2,3]
}
```

#### Success Response
- `200 OK` → Objeto Evento

#### Notes
- Cria um Invite para cada convidado com estado **pendente**
- Se existir Connection entre criador e convidado, é criada uma mensagem no chat com marcador `[EVENT_INVITE]`

---

### Aceitar convite

- **URL:** `/api/events/{eventId}/accept/{userId}`
- **Method:** `POST`

#### Success Response
- `200 OK`

---

### Sair de evento

- **URL:** `/api/events/{eventId}/leave/{userId}`
- **Method:** `POST`

#### Success Response
- `200 OK`

---

### Cancelar evento

- **URL:** `/api/events/{eventId}/cancel/{userId}`
- **Method:** `POST`

#### Success Response
- `200 OK`

#### Error Response
- `403 Forbidden` → Utilizador não é o criador

#### Notes
- Marca todos os convites como **cancelados**
- Apaga mensagens do chat de grupo do evento

---

### Listar eventos do utilizador

- **URL:** `/api/events/user/{userId}`
- **Method:** `GET`
- 
#### Success Response
- `200 OK`
```json
[
  {
    "id": 5,
    "titulo": "Café Moodly",
    "descricao": "Encontro informal",
    "local": "Lisboa",
    "dataEvento": "2026-01-10T18:00:00",
    "estado": "pendente",
    "isOwner": false,
    "criadorNome": "Joana"
  }
]
```
---

## Chats

### Enviar mensagem (chat direto)

- **URL:** `/api/chats/connection/{connectionId}/send`
- **Method:** `POST`

#### Data Params (JSON)
```json
{
  "autorId":1
  "conteudo": "Olá"
}
```

#### Success Response
- `200 OK`
```json
{
 "id": 99,
 "autorId": 1, 
"autorNome": "Joana",
 "conteudo": "Olá!",
 "dataEnvio": "..."
 }
```

#### Error Responses
- `400 Bad Request` → `"Dados inválidos"`
- `403 Forbidden` → `"Ligação ainda não aceite"` ou `"Autor não pertence a esta ligação"`

---

### Listar mensagens do chat direto

- **URL:** `/api/chats/connection/{connectionId}/messages`
- **Method:** `GET`

#### Success Response
- `200 OK` → Lista de mensagens

#### Error Response
- `403 Forbidden` → Conexão não aceite

---

### Listar pré-visualizações de chats diretos

- **URL:** `/api/chats/user/{userId}/connections`
- **Method:** `GET`

#### Success Response
- `200 OK`
```json
[
  {
    "connectionId": 10,
    "otherUserId": 2,
    "otherUserName": "Rui",
    "otherUserPhoto": "/uploads/profile/...",
    "lastMessage": "Olá",
    "lastMessageTime": "..."
  }
]
```
---

### Enviar mensagem no chat de evento (grupo)

- **URL:** `/api/chats/event/{eventId}/send`
- **Method:** `POST`

#### Data Params (JSON)
```json
{
 "autorId":2,"conteudo": "vamos"
}
```

#### Success Response
- `200 OK` → Mensagem

#### Error Response
- `403 Forbidden` → Utilizador não é criador nem tem convite aceite

---

### Listar mensagens do chat de evento

- **URL:** `/api/chats/event/{eventId}/messages`
- **Method:** `GET`

#### Success Response
- `200 OK` → Lista de mensagens

---

### Listar eventos com chat para um utilizador

- **URL:** `/api/chats/events-for-user/{userId}`
- **Method:** `GET`

#### Success Response
- `200 OK`
```json
{
"eventoId": 5, 
"titulo": "Party" 
}
```
---

## Nota

Como referência para a estrutura desta documentação usamos:
https://www.bocoup.com/blog/documenting-your-api  
(conforme indicado na aula **Spring Boot REST**).

