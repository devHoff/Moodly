package pt.iade.ei.firstapp

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.data.repository.ProfileRepository
import pt.iade.ei.firstapp.ui.components.InterestInputCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Select(navController: NavController) {
    var music by remember { mutableStateOf(SessionManager.music) }
    var movies by remember { mutableStateOf(SessionManager.movies) }
    var games by remember { mutableStateOf(SessionManager.games) }

    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val profileRepo = remember { ProfileRepository() }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2D004B))
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Os teus interesses",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D004B))
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Ajuda-nos a encontrar pessoas como tu",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Escreve alguns interesses, separados por vírgulas",
                color = Color.LightGray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))


            InterestInputCard(
                iconRes = R.drawable.musica,
                label = "Música",
                value = music,
                onValueChange = { music = it },
                placeholder = "Ex: Arctic Monkeys, Drake, Billie Eilish"
            )

            InterestInputCard(
                iconRes = R.drawable.filme,
                label = "Séries e filmes",
                value = movies,
                onValueChange = { movies = it },
                placeholder = "Ex: Breaking Bad, Interstellar, One Piece"
            )

            InterestInputCard(
                iconRes = R.drawable.jogo,
                label = "Jogos",
                value = games,
                onValueChange = { games = it },
                placeholder = "Ex: CS:GO, Minecraft, Hollow Knight"
            )


            Spacer(modifier = Modifier.height(24.dp))

            if (error != null) {
                Text(text = error ?: "", color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate("Pic")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Pular")
                }
                Button(
                    onClick = {
                        scope.launch {
                            val userId = SessionManager.userId
                            if (userId == null) {
                                error = "Utilizador inválido, faz login outra vez."
                                return@launch
                            }

                            loading = true
                            error = null

                            try {
                                SessionManager.music = music
                                SessionManager.movies = movies
                                SessionManager.games = games

                                fun splitInterests(text: String): List<String> =
                                    text.split(",")
                                        .map { it.trim() }
                                        .filter { it.isNotEmpty() }

                                val musicList = splitInterests(music)
                                val moviesList = splitInterests(movies)
                                val gamesList = splitInterests(games)

                                profileRepo.updateProfile(
                                    userId = userId,
                                    fotoPerfil = SessionManager.fotoPerfil,
                                    music = musicList,
                                    movies = moviesList,
                                    games = gamesList
                                )

                                navController.navigate("Pic")
                            } catch (e: Exception) {
                                error = e.message ?: "Erro ao guardar interesses"
                            } finally {
                                loading = false
                            }
                        }
                    },
                    enabled = !loading,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFD600),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (loading) "A guardar..." else "Guardar")
                }
            }
        }
    }
}
