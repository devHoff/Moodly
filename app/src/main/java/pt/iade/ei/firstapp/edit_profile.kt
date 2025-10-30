package pt.iade.ei.firstapp

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import coil.compose.AsyncImage
import pt.iade.ei.firstapp.ui.components.InterestInputCard

@Composable
fun EditProfileScreen(
    currentImageUri: Uri? = null,
    currentMusic: String = "",
    currentMovies: String = "",
    currentGames: String = "",
    onSaveClick: (Uri?, String, String, String) -> Unit,
    onCancelClick: () -> Unit
) {
    var selectedImageUri by remember { mutableStateOf(currentImageUri) }
    var music by remember { mutableStateOf(currentMusic) }
    var movies by remember { mutableStateOf(currentMovies) }
    var games by remember { mutableStateOf(currentGames) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D004B))
            .padding(16.dp)
    ) {
        Text(
            text = "Editar Perfil",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Picture Editor
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(140.dp)
                .clip(CircleShape)
                .background(Color(0xFF3C0063))
                .clickable { imagePickerLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(CircleShape)
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

        // Interest Inputs (same as before)
        InterestInputCard(
            iconRes = R.drawable.music,
            label = "Músicas",
            value = music,
            onValueChange = { music = it },
            placeholder = "Ex: Arctic Monkeys, Drake, Billie Eilish"
        )

        InterestInputCard(
            iconRes = R.drawable.movies,
            label = "Filmes e Séries",
            value = movies,
            onValueChange = { movies = it },
            placeholder = "Ex: Breaking Bad, Interstellar, One Piece"
        )

        InterestInputCard(
            iconRes = R.drawable.games,
            label = "Jogos",
            value = games,
            onValueChange = { games = it },
            placeholder = "Ex: CS:GO, Minecraft, Hollow Knight"
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Save + Cancel buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { onCancelClick() },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cancelar")
            }

            Button(
                onClick = { onSaveClick(selectedImageUri, music, movies, games) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD600),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF2D004B)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen(
        onSaveClick = { _, _, _, _ -> },
        onCancelClick = {}
    )
}
