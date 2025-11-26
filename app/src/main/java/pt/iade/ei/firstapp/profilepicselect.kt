package pt.iade.ei.firstapp

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.launch
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.data.repository.ProfileRepository

@Composable
fun ProfilePicSelectionScreen(
    navController: NavController,
    onNextClick: (Uri?) -> Unit,
    onSkipClick: () -> Unit
) {
    var selectedImageUri by remember {
        mutableStateOf(SessionManager.fotoPerfil?.let { Uri.parse(it) })
    }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    val scope = rememberCoroutineScope()
    val profileRepo = remember { ProfileRepository() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D004B))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Moodly Logo",
            tint = Color.Unspecified,
            modifier = Modifier.size(70.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Escolhe a tua foto de perfil",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Profile picture preview + click to open gallery
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(Color(0xFF3C0063))
                .clickable {
                    imagePickerLauncher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Selected profile picture",
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
                        // guarda no SessionManager
                        SessionManager.fotoPerfil = selectedImageUri?.toString()

                        fun splitInterests(text: String): List<String> =
                            text.split(",")
                                .map { it.trim() }
                                .filter { it.isNotEmpty() }

                        val musicList = splitInterests(SessionManager.music)
                        val moviesList = splitInterests(SessionManager.movies)
                        val gamesList = splitInterests(SessionManager.games)

                        profileRepo.updateProfile(
                            userId = userId,
                            fotoPerfil = SessionManager.fotoPerfil,
                            music = musicList,
                            movies = moviesList,
                            games = gamesList
                        )

                        onNextClick(selectedImageUri)
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    } catch (e: Exception) {
                        error = e.message ?: "Erro ao guardar perfil"
                    } finally {
                        loading = false
                    }
                }
            },
            enabled = !loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD600),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                if (loading) "A guardar..." else "Registar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onSkipClick) {
            Text(
                text = "Pular por agora",
                color = Color(0xFFFFD600),
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePicSelectionPreview() {
    val navController = rememberNavController()

    ProfilePicSelectionScreen(
        navController = navController,
        onNextClick = {},
        onSkipClick = {}
    )
}



