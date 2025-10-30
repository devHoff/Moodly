package pt.iade.ei.firstapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.ei.firstapp.R


@Composable
fun SelectInterestsScreen(
    onSaveClick: (music: String, movies: String, games: String) -> Unit
) {
    val music = remember { mutableStateOf("") }
    val movies = remember { mutableStateOf("") }
    val games = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D004B)) // dark purple background
            .padding(16.dp)
    ) {
        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.size(32.dp)) // placeholder for settings icon
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Moodly Logo",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Escolhe os teus interesses",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Music section
        InterestInputCard(
            iconRes = R.drawable.music,
            label = "Músicas",
            value = music.value,
            onValueChange = { music.value = it },
            placeholder = "Ex: Arctic Monkeys, Drake, Billie Eilish"
        )

        // Movies section
        InterestInputCard(
            iconRes = R.drawable.movies,
            label = "Filmes e Séries",
            value = movies.value,
            onValueChange = { movies.value = it },
            placeholder = "Ex: Breaking Bad, Interstellar, One Piece"
        )

        // Games section
        InterestInputCard(
            iconRes = R.drawable.games,
            label = "Jogos",
            value = games.value,
            onValueChange = { games.value = it },
            placeholder = "Ex: CS:GO, Minecraft, Hollow Knight"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onSaveClick(music.value, movies.value, games.value) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD600),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Guardar Interesses", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun InterestInputCard(
    iconRes: Int,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3C0063)), // lighter purple
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    tint = Color.Unspecified, // keeps original icon colors
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(label, color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder, color = Color.LightGray) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color(0xFFFFD600),
                    focusedIndicatorColor = Color(0xFFFFD600),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF2D004B)
@Composable
fun SelectInterestsScreenPreview() {
    SelectInterestsScreen { _, _, _ -> }
}