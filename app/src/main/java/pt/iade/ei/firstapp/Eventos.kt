package pt.iade.ei.firstapp

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.data.repository.EventRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Even(navController: NavController) {
    val currentUserId = SessionManager.userId
    val eventRepo = remember { EventRepository() }
    val scope = rememberCoroutineScope()

    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var events by remember { mutableStateOf<List<EventRepository.UiEvent>>(emptyList()) }

    var dialogEvent by remember { mutableStateOf<EventRepository.UiEvent?>(null) }
    var dialogType by remember { mutableStateOf<String?>(null) }
    var dialogError by remember { mutableStateOf<String?>(null) }

    fun reload() {
        val uid = currentUserId ?: return
        scope.launch {
            loading = true
            error = null
            val res = withContext(Dispatchers.IO) {
                eventRepo.loadEvents(uid)
            }
            res.onSuccess { list ->
                events = list
            }.onFailure { e ->
                error = e.message ?: "Erro ao carregar eventos"
            }
            loading = false
        }
    }

    LaunchedEffect(currentUserId) {
        if (currentUserId == null) {
            error = "Utilizador inválido. Faz login novamente."
        } else {
            reload()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Moodly",
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Eventos",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = { navController.navigate("createEventStep1") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFD600),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .padding(end = 16.dp)
                    ) {
                        Text(
                            text = "Criar",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF190A1C),
                    titleContentColor = Color.White
                )
            )
        },
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
                            colorFilter = ColorFilter.tint(Color.Yellow),
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    IconButton(onClick = { navController.navigate("cone") }) {
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

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFF2D004B))
        ) {
            if (error != null) {
                Text(
                    text = error ?: "",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            if (loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFFFFD600))
                }
            } else if (events.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ainda não tens eventos.\nCria um ou aceita um convite no chat.",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(events) { ev ->
                        EventCardItem(
                            event = ev,
                            onDetailsClick = {
                                val title = Uri.encode(ev.titulo)
                                val org = Uri.encode(ev.criadorNome ?: "")
                                val date = Uri.encode(ev.dataFormatted)
                                val loc = Uri.encode(ev.local ?: "")
                                val desc = Uri.encode(ev.desc ?: "")
                                navController.navigate(
                                    "eventDetail/${ev.id}/$title/$org/$date/$loc/$desc"
                                )
                            },
                            onChatClick = {
                                if (!ev.isCancelled) {
                                    navController.navigate("eventChat/${ev.id}/${ev.titulo}")
                                }
                            },
                            onLongPress = {
                                dialogEvent = ev
                                dialogError = null
                                dialogType = when {
                                    ev.isCancelled -> "hide"
                                    ev.isOwner -> "cancel"
                                    else -> "leave"
                                }
                            },
                            onHideClick = {
                                dialogEvent = ev
                                dialogError = null
                                dialogType = "hide"
                            }
                        )
                    }
                }
            }
        }
    }

    val selected = dialogEvent
    val type = dialogType
    if (selected != null && type != null && currentUserId != null) {
        EventActionDialog(
            event = selected,
            type = type,
            error = dialogError,
            onErrorChange = { dialogError = it },
            onDismiss = {
                dialogEvent = null
                dialogType = null
                dialogError = null
            },
            onConfirm = {
                scope.launch {
                    dialogError = null
                    when (type) {
                        "leave" -> {
                            val res = withContext(Dispatchers.IO) {
                                eventRepo.leave(selected.id, currentUserId)
                            }
                            res.onSuccess {
                                reload()
                                dialogEvent = null
                                dialogType = null
                                dialogError = null
                            }.onFailure { e ->
                                dialogError = e.message ?: "Erro ao cancelar ida"
                            }
                        }
                        "cancel" -> {
                            val res = withContext(Dispatchers.IO) {
                                eventRepo.cancel(selected.id, currentUserId)
                            }
                            res.onSuccess {
                                reload()
                                dialogEvent = null
                                dialogType = null
                                dialogError = null
                            }.onFailure { e ->
                                dialogError = e.message ?: "Erro ao cancelar evento"
                            }
                        }
                        "hide" -> {
                            val res = withContext(Dispatchers.IO) {
                                eventRepo.hide(selected.id, currentUserId)
                            }
                            res.onSuccess {
                                reload()
                                dialogEvent = null
                                dialogType = null
                                dialogError = null
                            }.onFailure { e ->
                                dialogError = e.message ?: "Erro ao apagar evento"
                            }
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventCardItem(
    event: EventRepository.UiEvent,
    onDetailsClick: () -> Unit,
    onChatClick: () -> Unit,
    onLongPress: () -> Unit,
    onHideClick: () -> Unit
) {
    val organizerLabel =
        if (event.isOwner) "Organizador: Tu"
        else "Organizador: ${event.criadorNome ?: ""}".trim()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onDetailsClick,
                onLongClick = onLongPress
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF3C0063),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = event.titulo,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = organizerLabel,
                fontSize = 14.sp,
                color = Color(0xFFFFD600)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Data: ${event.dataFormatted}",
                fontSize = 14.sp
            )

            if (event.isCancelled) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Evento cancelado",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF5555)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onHideClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF5555),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Apagar evento")
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDetailsClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Mais detalhes")
                    }
                    Button(
                        onClick = onChatClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFD600),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Chat")
                    }
                }
            }
        }
    }
}

@Composable
fun EventActionDialog(
    event: EventRepository.UiEvent,
    type: String,
    error: String?,
    onErrorChange: (String?) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val title = when (type) {
        "leave" -> "Cancelar ida"
        "cancel" -> "Cancelar evento"
        "hide" -> "Apagar evento"
        else -> ""
    }
    val message = when (type) {
        "leave" -> "Tens a certeza que queres cancelar a tua participação neste evento?"
        "cancel" -> "Tens a certeza que queres cancelar este evento para todos os convidados?"
        "hide" -> "Queres remover este evento cancelado da tua lista?"
        else -> ""
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title, fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                Text(text = message)
                if (error != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = error, color = Color.Red, fontSize = 13.sp)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
