package pt.iade.ei.firstapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import pt.iade.ei.firstapp.ui.auth.AuthViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    // 👇 default instances so callers can just pass navController
    profileViewModel: ProfileViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val session by authViewModel.session.collectAsStateWithLifecycle()

    // Push session data into ProfileViewModel when user is known
    LaunchedEffect(session.userId) {
        if (session.userId != null) {
            profileViewModel.userName = session.nome ?: ""
            profileViewModel.profileImageUrl = session.fotoPerfil
            // TODO: profileViewModel.connectionsCount = repo.getConnectionsCount(session.userId!!)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D004B))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFF3C0063)),
            contentAlignment = Alignment.Center
        ) {
            val photo = profileViewModel.profileImageUrl
            if (!photo.isNullOrBlank()) {
                AsyncImage(
                    model = photo,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = profileViewModel.userName,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "${profileViewModel.connectionsCount} Conexões",
            color = Color(0xFFFFD600),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("edit") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD600),
                contentColor = Color.Black
            )
        ) {
            Text("Editar Perfil", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))

        if (profileViewModel.music.isNotBlank()) {
            Text("Músicas: ${profileViewModel.music}", color = Color.White)
        }
        if (profileViewModel.movies.isNotBlank()) {
            Text("Filmes/Séries: ${profileViewModel.movies}", color = Color.White)
        }
        if (profileViewModel.games.isNotBlank()) {
            Text("Jogos: ${profileViewModel.games}", color = Color.White)
        }
    }
}
