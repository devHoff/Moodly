package pt.iade.ei.firstapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.data.remote.ChatApi
import pt.iade.ei.firstapp.data.repository.ChatRepository
import pt.iade.ei.firstapp.data.repository.EventRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen(
    navController: NavController,
    connectionId: Long,
    otherUserId: Long,
    otherUserName: String
) {
    val chatRepository = remember { ChatRepository() }
    val eventRepository = remember { EventRepository() }
    val currentUserId = SessionManager.userId
    val scope = rememberCoroutineScope()

    var messages by remember { mutableStateOf<List<ChatApi.ChatMessageDTO>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var input by remember { mutableStateOf("") }

    LaunchedEffect(connectionId) {
        if (currentUserId == null) {
            error = "Utilizador inválido. Faz login outra vez."
            return@LaunchedEffect
        }

        loading = true
        error = null

        val result = withContext(Dispatchers.IO) {
            chatRepository.getConnectionMessages(connectionId)
        }

        result.onSuccess { list ->
            messages = list
        }.onFailure { e ->
            error = e.message ?: "Erro ao carregar mensagens"
        }

        loading = false
    }

    fun sendMessage() {
        if (currentUserId == null) {
            error = "Sessão inválida"
            return
        }
        val text = input.trim()
        if (text.isBlank()) return

        input = ""

        val tempMessage = ChatApi.ChatMessageDTO(
            id = null,
            autorId = currentUserId,
            autorNome = null,
            conteudo = text,
            dataEnvio = null
        )
        messages = messages + tempMessage

        scope.launch {
            val result = withContext(Dispatchers.IO) {
                chatRepository.sendConnectionMessage(connectionId, currentUserId, text)
            }
            result.onSuccess { saved ->
                messages = messages.dropLast(1) + saved
            }.onFailure {
                error = "Erro ao enviar mensagem"
            }
        }
    }

    fun onInviteClick(eventId: Long, title: String) {
        val uid = currentUserId ?: return
        scope.launch {
            withContext(Dispatchers.IO) {
                eventRepository.accept(eventId, uid)
            }
            navController.navigate("eve")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF190A1C),
                    titleContentColor = Color.White
                ),
                title = {
                    Text(
                        text = otherUserName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D004B))
                .padding(padding)
        ) {
            if (error != null) {
                Text(
                    text = error ?: "",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                if (loading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "A carregar mensagens...",
                            color = Color.White
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        items(messages) { msg ->
                            val content = msg.conteudo ?: ""
                            val isInvite = content.startsWith("[EVENT_INVITE]")

                            if (isInvite) {
                                val body = content.removePrefix("[EVENT_INVITE]")
                                val parts = body.split("|")
                                val eventId = parts.getOrNull(0)?.toLongOrNull()
                                val title = parts.getOrNull(1) ?: "Evento"

                                if (eventId != null) {
                                    EventInviteBubble(
                                        titulo = title,
                                        onClick = { onInviteClick(eventId, title) }
                                    )
                                }
                            } else {
                                val isMine = currentUserId != null && msg.autorId == currentUserId
                                MessageBubble(message = msg, isMine = isMine)
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.weight(1f),
                    placeholder = {
                        Text(
                            text = "Escreve uma mensagem...",
                            color = Color.LightGray
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF3C0063),
                        unfocusedContainerColor = Color(0xFF3C0063),
                        disabledContainerColor = Color(0xFF3C0063),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedTextColor = Color(0xFFFFF8E1),
                        unfocusedTextColor = Color(0xFFFFF8E1),
                        disabledTextColor = Color(0xFFFFF8E1),
                    ),
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.size(8.dp))

                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(Color(0xFFFFD600), RoundedCornerShape(23.dp))
                        .clickable { sendMessage() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "➤",
                        color = Color(0xFF190A1C),
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBubble(
    message: ChatApi.ChatMessageDTO,
    isMine: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (isMine) Color(0xFFFFD600) else Color(0xFF3C0063),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = message.conteudo ?: "",
                color = if (isMine) Color(0xFF190A1C) else Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun EventInviteBubble(
    titulo: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFFFD600), RoundedCornerShape(16.dp))
                .clickable { onClick() }
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Column {
                Text(
                    text = "Convite para evento",
                    color = Color(0xFF190A1C),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = titulo,
                    color = Color(0xFF190A1C)
                )
                Text(
                    text = "Clique para participar",
                    color = Color(0xFF190A1C),
                    fontSize = 12.sp
                )
            }
        }
    }
}
