package pt.iade.ei.firstapp





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