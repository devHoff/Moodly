package pt.iade.ei.firstapp.eventos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.data.remote.ConnectionApi
import pt.iade.ei.firstapp.data.repository.ConnectionRepository
import pt.iade.ei.firstapp.data.repository.EventRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventStep2(
    nav: NavController,
    dataStr: String
) {
    val parts = dataStr.split("|")
    val titulo = parts.getOrNull(0) ?: ""
    val desc = parts.getOrNull(1) ?: ""
    val local = parts.getOrNull(2) ?: ""
    val data = parts.getOrNull(3) ?: ""

    val eventRepo = remember { EventRepository() }
    val connRepo = remember { ConnectionRepository() }
    val scope = rememberCoroutineScope()

    var connections by remember { mutableStateOf<List<ConnectionApi.OutgoingConnectionDTO>>(emptyList()) }
    var selected by remember { mutableStateOf<Set<Long>>(emptySet()) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val userId = SessionManager.userId ?: return@LaunchedEffect
        val res = withContext(Dispatchers.IO) { connRepo.outgoingConnections(userId) }
        res.onSuccess { list ->
            connections = list.filter { it.mutual }
        }.onFailure { e ->
            error = e.message ?: "Erro a carregar conexões"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Convidar pessoas", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF190A1C),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF2D004B)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFF2D004B)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3C0063),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Seleciona as tuas conexões para o evento:",
                        color = Color.White
                    )
                    Text(
                        text = "\"$titulo\"",
                        color = Color(0xFFFFD600),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (error != null) {
                        Text(
                            text = error ?: "",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (connections.isEmpty()) {
                        Text(
                            text = "Ainda não tens conexões mútuas.",
                            color = Color.White
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f, fill = false)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(connections) { c ->
                                val checked = c.userId != null && c.userId in selected
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            val id = c.userId ?: return@clickable
                                            selected =
                                                if (id in selected) selected - id else selected + id
                                        }
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = c.nome ?: "Utilizador",
                                        color = Color.White
                                    )
                                    Checkbox(
                                        checked = checked,
                                        onCheckedChange = {
                                            val id = c.userId ?: return@Checkbox
                                            selected =
                                                if (id in selected) selected - id else selected + id
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = { nav.popBackStack() }) {
                            Text("Voltar", color = Color.White)
                        }

                        Button(
                            onClick = {
                                val uid = SessionManager.userId ?: return@Button
                                if (titulo.isBlank()) {
                                    error = "Título em falta."
                                    return@Button
                                }
                                loading = true
                                error = null
                                scope.launch {
                                    val res = withContext(Dispatchers.IO) {
                                        eventRepo.create(
                                            cid = uid,
                                            tit = titulo,
                                            desc = if (desc.isBlank()) null else desc,
                                            loc = if (local.isBlank()) null else local,
                                            data = if (data.isBlank()) null else data,
                                            convidadoIds = selected.toList()
                                        )
                                    }
                                    loading = false
                                    res.onSuccess {
                                        nav.popBackStack("eve", inclusive = false)
                                        nav.navigate("eve")
                                    }.onFailure { e ->
                                        error = e.message ?: "Erro ao criar evento"
                                    }
                                }
                            },
                            enabled = !loading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFD600),
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(if (loading) "A criar..." else "Finalizar")
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFF2D004B)
@Composable
fun CreateEventStep2Preview() {
    val nav = rememberNavController()
    CreateEventStep2(
        nav = nav,
        dataStr = "Festa|Música e diversão|Lisboa|2025-01-01T20:00:00"
    )
}

