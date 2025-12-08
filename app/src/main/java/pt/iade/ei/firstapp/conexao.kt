package pt.iade.ei.firstapp.activities

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.iade.ei.firstapp.R
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.data.remote.ConnectionApi
import pt.iade.ei.firstapp.data.remote.buildImageUrl
import pt.iade.ei.firstapp.data.repository.ConnectionRepository

@Composable
fun Conex(navController: NavController) {
    val repo = remember { ConnectionRepository() }
    val uid = SessionManager.userId

    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var connections by remember { mutableStateOf<List<ConnectionApi.OutgoingConnectionDTO>>(emptyList()) }

    LaunchedEffect(uid) {
        if (uid == null) {
            error = "Utilizador inválido."
            return@LaunchedEffect
        }

        loading = true
        val result = withContext(Dispatchers.IO) { repo.outgoingConnections(uid) }

        result.onSuccess { list ->
            val hidden = SessionManager.hiddenChatConnections
            connections = list.filter { it.connectionId !in hidden }
            SessionManager.connectionsCount = connections.count { it.mutual }
        }.onFailure {
            error = it.message ?: "Erro ao carregar conexões."
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
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.navigate("eve") }) {
                        Image(painterResource(R.drawable.oi), null, Modifier.size(36.dp))
                    }
                    IconButton(onClick = {}) {
                        Image(painterResource(R.drawable.event), null, Modifier.size(36.dp))
                    }
                    IconButton(onClick = { navController.navigate("home") }) {
                        Image(painterResource(R.drawable.mood), null, Modifier.size(36.dp))
                    }
                    IconButton(onClick = { navController.navigate("chats") }) {
                        Icon(Icons.Default.Message, null, tint = Color.White, modifier = Modifier.size(30.dp))
                    }
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.AccountBox, null, tint = Color.White, modifier = Modifier.size(30.dp))
                    }
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D004B))
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("As tuas conexões", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(8.dp))
            Text("Utilizadores com quem clicaste em conectar.", color = Color.LightGray)

            Spacer(Modifier.height(16.dp))

            when {
                loading -> CircularProgressIndicator(color = Color(0xFFFFD600))

                error != null -> Text(error!!, color = Color.Red)

                connections.isEmpty() -> Text(
                    "Sem pedidos de conexão pendentes.\nVai à aba conectar!",
                    color = Color.LightGray
                )

                else -> LazyColumn {
                    items(connections) { c ->
                        ConnectionCardItem(connection = c) {

                            if (c.mutual && c.connectionId != null) {
                                SessionManager.hiddenChatConnections.add(c.connectionId)
                                connections = connections.filter { it.connectionId != c.connectionId }

                                val name = Uri.encode(c.nome ?: "")
                                navController.navigate("chatroom/${c.connectionId}/${c.userId}/$name")
                            }
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
    onClick: () -> Unit
) {
    val highlight = Color(0xFFFFD600)
    val photoUrl = buildImageUrl(connection.fotoPerfil)

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3C0063)),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .then(
                if (connection.mutual) Modifier.border(2.dp, highlight, RoundedCornerShape(20.dp))
                else Modifier
            )
            .clickable(enabled = connection.mutual) { onClick() }
    ) {

        Row(
            Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (photoUrl != null) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    modifier = Modifier.size(56.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    Modifier.size(56.dp).clip(CircleShape).background(Color(0xFF190A1C)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(connection.nome?.firstOrNull()?.uppercase() ?: "?", color = Color.White)
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(connection.nome ?: "Moodler", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)

                if (connection.mutual) {
                    Text("Vocês já se conectaram! 🎉", color = highlight, fontSize = 14.sp)
                    Spacer(Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Message, null, tint = highlight, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Clique para iniciar um chat", color = highlight)
                    }
                } else {
                    Text(
                        "À espera que ${connection.nome ?: "o outro utilizador"} também conecte contigo.",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
