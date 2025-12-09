package pt.iade.ei.firstapp

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.data.remote.ChatApi
import pt.iade.ei.firstapp.data.remote.ProfileApi
import pt.iade.ei.firstapp.data.remote.buildImageUrl
import pt.iade.ei.firstapp.data.repository.ChatRepository
import pt.iade.ei.firstapp.data.repository.EventRepository
import pt.iade.ei.firstapp.data.repository.ProfileRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventChatScreen(
    nav: NavController,
    eventId: Long,
    title: String
) {
    val chatRepo = remember { ChatRepository() }
    val eventRepo = remember { EventRepository() }
    val profileRepo = remember { ProfileRepository() }
    val currentUserId = SessionManager.userId
    val scope = rememberCoroutineScope()

    var messages by remember { mutableStateOf<List<ChatApi.ChatMessageDTO>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var input by remember { mutableStateOf("") }

    var profiles by remember { mutableStateOf<Map<Long, ProfileApi.UserProfileResponse>>(emptyMap()) }

    LaunchedEffect(eventId) {
        if (currentUserId == null) {
            error = "Utilizador inválido. Faz login outra vez."
            return@LaunchedEffect
        }

        loading = true
        error = null

        val result = withContext(Dispatchers.IO) {
            chatRepo.getEventMessages(eventId)
        }

        result.onSuccess { list ->
            messages = list
        }.onFailure { e ->
            error = e.message ?: "Erro ao carregar mensagens"
        }

        loading = false
    }

    LaunchedEffect(messages) {
        val ids = messages.map { it.autorId }.distinct()
        val missing = ids.filter { it !in profiles.keys }
        if (missing.isNotEmpty()) {
            val newProfiles = mutableMapOf<Long, ProfileApi.UserProfileResponse>()
            for (id in missing) {
                try {
                    val p = withContext(Dispatchers.IO) {
                        profileRepo.getProfile(id)
                    }
                    newProfiles[id] = p
                } catch (_: Exception) {
                }
            }
            if (newProfiles.isNotEmpty()) {
                profiles = profiles + newProfiles
            }
        }
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
            autorNome = "Tu",
            conteudo = text,
            dataEnvio = null
        )
        messages = messages + tempMessage

        scope.launch {
            val result = withContext(Dispatchers.IO) {
                chatRepo.sendEventMessage(eventId, currentUserId, text)
            }
            result.onSuccess { saved ->
                messages = messages.dropLast(1) + saved
            }.onFailure {
                error = "Erro ao enviar mensagem"
            }
        }
    }

    fun leaveEvent() {
        val uid = currentUserId ?: return
        scope.launch {
            withContext(Dispatchers.IO) {
                eventRepo.leave(eventId, uid)
            }
            nav.popBackStack()
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
                    Column {
                        Text(
                            text = title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Chat do evento",
                            fontSize = 12.sp,
                            color = Color(0xFFFFD600)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },

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
                            val isMine = currentUserId != null && msg.autorId == currentUserId
                            val profile = profiles[msg.autorId]
                            EventChatMessageBubble(
                                message = msg,
                                profile = profile,
                                isMine = isMine
                            )
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
fun EventChatMessageBubble(
    message: ChatApi.ChatMessageDTO,
    profile: ProfileApi.UserProfileResponse?,
    isMine: Boolean
) {
    val name = when {
        isMine -> "Tu"
        !message.autorNome.isNullOrBlank() -> message.autorNome
        else -> profile?.nome
    } ?: "Utilizador"

    val photoUrl = buildImageUrl(profile?.fotoPerfil)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        if (!isMine) {
            if (photoUrl != null) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF190A1C)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = name.take(1).uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
        }

        Column(
            horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
        ) {
            Text(
                text = name,
                color = Color(0xFFFFD600),
                fontSize = 12.sp
            )

            Box(
                modifier = Modifier
                    .background(
                        color = if (isMine) Color(0xFFFFD600) else Color(0xFF3C0063),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = message.conteudo,
                    color = if (isMine) Color(0xFF190A1C) else Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (isMine) {
            Spacer(modifier = Modifier.size(8.dp))
            if (photoUrl != null) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF190A1C)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = name.take(1).uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFF2D004B)
@Composable
fun EventChatScreenPreview() {
    val nav = rememberNavController()

    val mockMessages = listOf(
        ChatApi.ChatMessageDTO(
            id = 1,
            autorId = 1,
            autorNome = "Tu",
            conteudo = "Olá pessoal!",
            dataEnvio = "2025-01-01T10:00"
        ),
        ChatApi.ChatMessageDTO(
            id = 2,
            autorId = 2,
            autorNome = "Maria",
            conteudo = "Olá! Tudo bem?",
            dataEnvio = "2025-01-01T10:01"
        )
    )

    EventChatPreviewContent(nav, mockMessages)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun EventChatPreviewContent(
    nav: NavController,
    messages: List<ChatApi.ChatMessageDTO>
) {
    Scaffold(
        containerColor = Color(0xFF2D004B)
    ) {
        LazyColumn(Modifier.padding(16.dp)) {
            items(messages) { msg ->
                EventChatMessageBubble(
                    message = msg,
                    profile = null,
                    isMine = msg.autorId.toInt() == 1
                )
            }
        }
    }
}

