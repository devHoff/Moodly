package pt.iade.ei.firstapp

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.data.repository.ProfileRepository
import pt.iade.ei.firstapp.data.uriToMultipart
import pt.iade.ei.firstapp.ui.components.InterestInputCard
import pt.iade.ei.firstapp.data.remote.buildImageUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun EditProfileScreen(
    userId: Long,
    onCancel: () -> Unit,
    onSaved: () -> Unit
) {
    val context = LocalContext.current
    var selectedImageUri by remember {
        mutableStateOf(SessionManager.fotoPerfil?.let { Uri.parse(it) })
    }
    var music by remember { mutableStateOf(SessionManager.music) }
    var movies by remember { mutableStateOf(SessionManager.movies) }
    var games by remember { mutableStateOf(SessionManager.games) }

    var updating by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    val scope = rememberCoroutineScope()
    val repo = remember { ProfileRepository() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Editar Perfil", color = Color.White, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF2D004B)
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2D004B)),
                color = Color(0xFF2D004B)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = onCancel,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("Cancelar") }

                    Button(
                        onClick = {
                            scope.launch {
                                if (userId <= 0) {
                                    error = "Utilizador inválido."
                                    return@launch
                                }
                                updating = true
                                error = null
                                try {
                                    SessionManager.music = music
                                    SessionManager.movies = movies
                                    SessionManager.games = games

                                    var fotoUrl: String? = SessionManager.fotoPerfil
                                    if (selectedImageUri != null) {
                                        val part = uriToMultipart(context, selectedImageUri!!)
                                        fotoUrl = repo.uploadProfilePhoto(userId, part).fotoPerfil
                                    }

                                    SessionManager.fotoPerfil = fotoUrl

                                    fun splitInterests(text: String): List<String> =
                                        text.split(",")
                                            .map { it.trim() }
                                            .filter { it.isNotEmpty() }

                                    val musicList = splitInterests(music)
                                    val moviesList = splitInterests(movies)
                                    val gamesList = splitInterests(games)

                                    repo.updateProfile(
                                        userId = userId,
                                        fotoPerfil = fotoUrl,
                                        music = musicList,
                                        movies = moviesList,
                                        games = gamesList
                                    )

                                    onSaved()
                                } catch (e: Exception) {
                                    error = e.message ?: "Erro ao guardar perfil"
                                } finally {
                                    updating = false
                                }
                            }
                        },
                        enabled = !updating,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFD600),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            if (updating) "A guardar..." else "Guardar",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D004B))
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3C0063))
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null) {
                    AsyncImage(
                        model = buildImageUrl(selectedImageUri.toString()),
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = "Pick Image",
                        tint = Color(0xFFFFD600),
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (error != null) {
                Text(text = error ?: "", color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }

            InterestInputCard(
                iconRes = R.drawable.musica,
                label = "Música",
                value = music,
                onValueChange = { music = it },
                placeholder = "Ex: Arctic Monkeys, Drake, Billie Eilish"
            )

            Spacer(modifier = Modifier.height(16.dp))

            InterestInputCard(
                iconRes = R.drawable.filme,
                label = "Filmes e séries",
                value = movies,
                onValueChange = { movies = it },
                placeholder = "Ex: Breaking Bad, Interstellar, One Piece"
            )

            Spacer(modifier = Modifier.height(16.dp))

            InterestInputCard(
                iconRes = R.drawable.jogo,
                label = "Jogos",
                value = games,
                onValueChange = { games = it },
                placeholder = "Ex: CS:GO, Minecraft, Hollow Knight"
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, backgroundColor = 0xFF2D004B)
@Composable
fun EditProfileScreenPreview() {
    val navController = rememberNavController()
    EditProfileScreen(
        userId = 1L,
        onCancel = {},
        onSaved = {}
    )
}
