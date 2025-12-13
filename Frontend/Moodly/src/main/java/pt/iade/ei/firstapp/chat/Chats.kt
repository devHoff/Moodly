package pt.iade.ei.firstapp.chat

import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.iade.ei.firstapp.R
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.data.remote.ChatApi
import pt.iade.ei.firstapp.data.remote.buildImageUrl
import pt.iade.ei.firstapp.data.repository.ChatRepository
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme

@Composable
fun ChatsScreen(navController: NavController) {
    val chatRepository = remember { ChatRepository() }
    val currentUserId = SessionManager.userId

    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var chats by remember { mutableStateOf<List<ChatApi.ConnectionChatDTO>>(emptyList()) }

    LaunchedEffect(currentUserId) {
        if (currentUserId == null) {
            error = "Utilizador inválido. Faz login outra vez."
            return@LaunchedEffect
        }

        loading = true
        error = null

        val result = withContext(Dispatchers.IO) {
            chatRepository.getConnectionChats(currentUserId)
        }

        result.onSuccess { list ->
            chats = list
        }.onFailure { e ->
            error = e.message ?: "Erro ao carregar chats"
        }

        loading = false
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF190A1C),
                modifier = Modifier.height(75.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.navigate("eve") },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.oi),
                            contentDescription = "Eventos",
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    IconButton(onClick = { navController.navigate("cone") }) {
                        Image(
                            painter = painterResource(id = R.drawable.conexao),
                            contentDescription = "Conexões",
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    IconButton(onClick = { navController.navigate("home") }) {
                        Image(
                            painter = painterResource(id = R.drawable.mood),
                            contentDescription = "Mood",
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Message,
                            contentDescription = "Mensagens",
                            tint = Color(0xFFFFD600),
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Perfil",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D004B))
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo Moodly",
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.TopEnd)
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Chats",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD600),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Conversa com as tuas conexões.",
                            fontSize = 14.sp,
                            color = Color.LightGray,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFFFFD600))
                    }
                } else if (error != null) {
                    Text(
                        text = error ?: "",
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                } else if (chats.isEmpty()) {
                    Text(
                        text = "Ainda não tens chats ativos.\nVai às tuas conexões e começa uma conversa",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(chats) { chat ->
                            ChatListItem(
                                chat = chat,
                                onClick = {
                                    val encodedName =
                                        Uri.encode(chat.otherUserName ?: "")
                                    navController.navigate(
                                        "chatroom/${chat.connectionId}/${chat.otherUserId}/$encodedName"
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatListItem(
    chat: ChatApi.ConnectionChatDTO,
    onClick: () -> Unit
) {
    val cardColor = Color(0xFF3C0063)
    val photoUrl = buildImageUrl(chat.otherUserPhoto)

    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (photoUrl != null) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF190A1C)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = chat.otherUserName?.take(1)?.uppercase() ?: "?",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.size(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = chat.otherUserName ?: "Moodler",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = chat.lastMessage ?: "Ainda sem mensagens. Diz olá 👋",
                    color = Color.LightGray,
                    fontSize = 13.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatsPreview() {
    FirstAppTheme {
        val navController = rememberNavController()
        ChatsScreen(navController = navController)
    }
}

