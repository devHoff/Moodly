package pt.iade.ei.firstapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalInspectionMode
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.data.remote.buildImageUrl
import pt.iade.ei.firstapp.data.repository.ConnectionRepository
import pt.iade.ei.firstapp.data.repository.UsuarioRepository
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme

data class SuggestedUser(
    val id: Long,
    val name: String,
    val photoUrl: String?,
    val music: List<String>,
    val movies: List<String>,
    val games: List<String>,
    val connectionsCount: Int
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConnectScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val connectionRepo = remember { ConnectionRepository() }
    val usuarioRepo = remember { UsuarioRepository() }

    var suggestions by remember { mutableStateOf<List<SuggestedUser>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var infoMessage by remember { mutableStateOf<String?>(null) }

    val currentUserId = SessionManager.userId
    val isPreview = LocalInspectionMode.current

    LaunchedEffect(Unit) {
        if (isPreview) {
            suggestions = listOf(
                SuggestedUser(
                    id = 2L,
                    name = "Joana",
                    photoUrl = null,
                    music = listOf("Drake", "Arctic Monkeys"),
                    movies = listOf("Inception", "Breaking Bad"),
                    games = listOf("Valorant", "FIFA"),
                    connectionsCount = 5
                ),
                SuggestedUser(
                    id = 3L,
                    name = "Ricardo",
                    photoUrl = null,
                    music = listOf("Jazz", "Lo-fi"),
                    movies = listOf("Interstellar"),
                    games = listOf("League of Legends"),
                    connectionsCount = 3
                )
            )
            loading = false
            error = null
            infoMessage = null
            return@LaunchedEffect
        }

        if (currentUserId == null) {
            error = "Utilizador inválido. Faz login outra vez."
            return@LaunchedEffect
        }

        loading = true
        error = null
        infoMessage = null

        try {
            val result = withContext(Dispatchers.IO) {
                connectionRepo.discoverUsers(currentUserId, limit = 20)
            }

            result.onFailure { e ->
                error = e.message ?: "Erro ao carregar sugestões"
            }.onSuccess { list ->
                val enriched = mutableListOf<SuggestedUser>()

                for (user in list) {
                    val detalheRes = withContext(Dispatchers.IO) {
                        usuarioRepo.getUsuarioDetalhe(user.id)
                    }

                    val musicList = mutableListOf<String>()
                    val moviesList = mutableListOf<String>()
                    val gamesList = mutableListOf<String>()

                    detalheRes.onSuccess { detalhe ->
                        detalhe.interesses.orEmpty().forEach { i ->
                            val tipo = i.tipo?.lowercase() ?: ""
                            val nome = i.nome ?: ""
                            if (nome.isBlank()) return@forEach
                            when {
                                "musica" in tipo || "music" in tipo -> musicList.add(nome)
                                "filme" in tipo || "film" in tipo || "serie" in tipo || "series" in tipo -> moviesList.add(nome)
                                "jogo" in tipo || "game" in tipo -> gamesList.add(nome)
                            }
                        }
                    }

                    val connectionsCount = withContext(Dispatchers.IO) {
                        connectionRepo.mutualConnections(user.id).getOrElse { emptyList() }.size
                    }

                    enriched.add(
                        SuggestedUser(
                            id = user.id,
                            name = user.nome ?: "Moodler",
                            photoUrl = user.fotoPerfil,
                            music = musicList,
                            movies = moviesList,
                            games = gamesList,
                            connectionsCount = connectionsCount
                        )
                    )
                }

                suggestions = enriched
                if (enriched.isEmpty()) {
                    infoMessage = "Ainda não há sugestões para ti. Tenta mais tarde ✨"
                }
            }
        } catch (e: Exception) {
            error = e.message ?: "Erro inesperado"
        } finally {
            loading = false
        }
    }

    val pagerState = rememberPagerState(pageCount = { maxOf(suggestions.size, 1) })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D004B))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Moodly Logo",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Encontra um amigo",
            color = Color(0xFFFFD600),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (loading && suggestions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFFFD600))
            }
            return@Column
        }

        if (error != null && suggestions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error ?: "",
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            }
            return@Column
        }

        if (infoMessage != null && suggestions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = infoMessage ?: "",
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
            return@Column
        }

        if (suggestions.isNotEmpty()) {
            VerticalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                val user = suggestions[page]

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ProfileSuggestionCard(user = user)

                    Spacer(modifier = Modifier.height(24.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            RowButtonsConnect(
                                onSkip = {
                                    scope.launch {
                                        val nextPage = if (page < suggestions.lastIndex) page + 1 else page
                                        pagerState.animateScrollToPage(nextPage)
                                    }
                                },
                                onConnect = {
                                    if (currentUserId == null || isPreview) return@RowButtonsConnect
                                    scope.launch {
                                        val res = withContext(Dispatchers.IO) {
                                            connectionRepo.sendConnectionRequest(currentUserId, user.id)
                                        }
                                        res.onSuccess { resp ->
                                            infoMessage =
                                                if (resp.mutual == true) "Conexão aceite! Vocês já são amigos 🎉"
                                                else "Pedido enviado para ${user.name}"
                                            if (page < suggestions.lastIndex) {
                                                pagerState.animateScrollToPage(page + 1)
                                            }
                                        }.onFailure { e ->
                                            infoMessage = e.message ?: "Erro ao enviar pedido"
                                        }
                                    }
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
private fun ProfileSuggestionCard(user: SuggestedUser) {
    val img = buildImageUrl(user.photoUrl)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(480.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3C0063)),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF190A1C)),
                contentAlignment = Alignment.Center
            ) {
                if (img != null) {
                    AsyncImage(
                        model = img,
                        contentDescription = "Foto de ${user.name}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.default_profile),
                        contentDescription = "Sem foto",
                        tint = Color.Unspecified,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = user.name,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${user.connectionsCount} conexões",
                color = Color(0xFFFFD600),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            InterestSection(label = "Música", icon = R.drawable.musica, values = user.music)
            Spacer(modifier = Modifier.height(8.dp))
            InterestSection(label = "Filmes e séries", icon = R.drawable.filme, values = user.movies)
            Spacer(modifier = Modifier.height(8.dp))
            InterestSection(label = "Jogos", icon = R.drawable.jogo, values = user.games)
        }
    }
}

@Composable
private fun InterestSection(label: String, icon: Int, values: List<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = Color(0xFFFFD600),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        val text = if (values.isNotEmpty()) values.joinToString(", ") else "Sem preferências definidas"
        Text(
            text = text,
            color = if (values.isNotEmpty()) Color.White else Color.LightGray,
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 32.dp, top = 2.dp)
        )
    }
}

@Composable
private fun RowButtonsConnect(
    onSkip: () -> Unit,
    onConnect: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onSkip,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Pular", fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onConnect,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD600),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Conectar", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, backgroundColor = 0xFF2D004B)
@Composable
fun ConnectScreenPreview() {
    val navController = rememberNavController()
    SessionManager.userId = 1L
    FirstAppTheme {
        ConnectScreen(navController = navController)
    }
}
