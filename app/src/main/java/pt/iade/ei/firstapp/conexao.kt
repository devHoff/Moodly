package pt.iade.ei.firstapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.data.remote.ConnectionApi
import pt.iade.ei.firstapp.data.remote.buildImageUrl
import pt.iade.ei.firstapp.data.repository.ConnectionRepository
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme

@Composable
fun Conex(navController: NavController) {
    val connectionRepo = remember { ConnectionRepository() }
    val currentUserId = SessionManager.userId

    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var connections by remember { mutableStateOf<List<ConnectionApi.OutgoingConnectionDTO>>(emptyList()) }

    LaunchedEffect(currentUserId) {
        if (currentUserId == null) {
            error = "Utilizador inválido. Faz login outra vez."
            return@LaunchedEffect
        }

        loading = true
        error = null

        val result = withContext(Dispatchers.IO) {
            connectionRepo.outgoingConnections(currentUserId)
        }

        result.onSuccess { list ->
            connections = list
            SessionManager.connectionsCount = list.count { it.mutual }
        }.onFailure { e ->
            error = e.message ?: "Erro ao carregar conexões"
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

                    IconButton(onClick = { }) {
                        Image(
                            painter = painterResource(id = R.drawable.event),
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

                    IconButton(onClick = { navController.navigate("chats") }) {
                        Icon(
                            imageVector = Icons.Default.Message,
                            contentDescription = "Mensagens",
                            tint = Color.White,
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
                Text(
                    text = "As tuas conexões",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Vê os utilizadores com quem clicaste em conectar.",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
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
                } else if (connections.isEmpty()) {
                    Text(
                        text = "Ainda não enviaste pedidos de conexão.\nVai à aba Mood para começar ✨",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(connections) { c ->
                            ConnectionCardItem(
                                connection = c,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ConnectionCardItem(
    connection: ConnectionApi.OutgoingConnectionDTO,
    navController: NavController
) {
    val highlight = Color(0xFFFFD600)
    val cardColor = Color(0xFF3C0063)

    val photoUrl = buildImageUrl(connection.fotoPerfil)

    val borderModifier = if (connection.mutual) {
        Modifier
            .border(
                width = 2.dp,
                color = highlight,
                shape = RoundedCornerShape(20.dp)
            )
    } else {
        Modifier
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .then(borderModifier)
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
                        .size(56.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF190A1C)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = connection.nome?.take(1)?.uppercase() ?: "?",
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
                    text = connection.nome ?: "Moodler",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (connection.mutual) {
                    Text(
                        text = "Vocês já se conectaram! 🎉",
                        color = highlight,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Message,
                            contentDescription = "Chat",
                            tint = highlight,
                            modifier = Modifier
                                .size(20.dp)
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(
                            text = "Clique para iniciar um chat",
                            color = highlight,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(end = 8.dp)
                        )
                    }
                } else {
                    Text(
                        text = "Você ainda não conectou com ${connection.nome ?: "este utilizador"}.",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Quando a outra pessoa também conectar contigo, este cartão ficará destacado a amarelo.",
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConexPreview() {
    FirstAppTheme {
        val navController = rememberNavController()
        Conex(navController = navController)
    }
}
