package pt.iade.ei.firstapp

    import android.annotation.SuppressLint
            import androidx.compose.foundation.Image
            import androidx.compose.foundation.background
            import androidx.compose.foundation.layout.*
            import androidx.compose.foundation.shape.RoundedCornerShape
            import androidx.compose.material.icons.Icons
            import androidx.compose.material.icons.filled.AccountBox
            import androidx.compose.material.icons.filled.Message
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


            @Composable
            fun ProfileScreen(navController: NavController,
                              profileViewModel: ProfileViewModel
            ) {
                Scaffold(
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
                                        contentDescription = "Events",
                                        modifier = Modifier.size(36.dp)
                                    )
                                }

                                IconButton(
                                    onClick = { navController.navigate("cone") }
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.event),
                                        contentDescription = "Connections",
                                        modifier = Modifier.size(36.dp)
                                    )
                                }

                                IconButton(
                                    onClick = {   navController.navigate("home")
                                    }
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.mood),
                                        contentDescription = "Mood",
                                        modifier = Modifier.size(36.dp)
                                    )
                                }

                                IconButton(
                                    onClick = { navController.navigate("chats") }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Message,
                                        contentDescription = "Messages",
                                        tint = Color.White,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }

                                IconButton(
                                    onClick = { navController.navigate("profile") }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccountBox,
                                        contentDescription = "Profile",
                                        tint = Color.Yellow,
                                        modifier = Modifier.size(30.dp)
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
                            .padding(20.dp)
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                // Navigate to settings
                                // navController.navigate("settings")
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.definicoes),
                                    contentDescription = "Settings",
                                    tint = Color(0xFFFFD600),
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Moodly Logo",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // 🔹 Profile Picture (rectangle)
                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .height(220.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFF3C0063)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (profileViewModel.profileImageUrl != null) {
                                AsyncImage(
                                    model = profileViewModel.profileImageUrl,
                                    contentDescription = "User Profile Picture",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(16.dp))
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.default_profile),
                                    contentDescription = "Default Profile Picture",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(16.dp))
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // 🔹 User name
                        Text(
                            text = profileViewModel.userName,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // 🔹 Connections
                        Text(
                            text = "${profileViewModel.connectionsCount} Conexões",
                            color = Color(0xFFFFD600),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // 🔹 Interests Section
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF3C0063), RoundedCornerShape(16.dp))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Interesses",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            InterestItem(icon = R.drawable.music, label = "Músicas", value = profileViewModel.music)
                            InterestItem(icon = R.drawable.movies, label = "Filmes e Séries", value = profileViewModel.movies)
                            InterestItem(icon = R.drawable.games, label = "Jogos", value = profileViewModel.games)
                        }

                        Spacer(modifier = Modifier.height(40.dp))


                        Button(
                            onClick = {
                                navController.navigate("edit")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFD600),
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Editar Perfil", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

    @Composable
    fun InterestItem(icon: Int, label: String, value: String) {
        Column(modifier = Modifier.padding(vertical = 6.dp)) {
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
            if (value.isNotBlank()) {
                Text(

                    text = value,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 32.dp, top = 2.dp)
                )
            } else {
                Text(
                    text = "Sem preferências definidas",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 32.dp, top = 2.dp)
                )
            }
        }
    }

    @SuppressLint("ViewModelConstructorInComposable")
    @Preview(showBackground = true, backgroundColor = 0xFF2D004B)
    @Composable
    fun ProfileScreenPreview() {
        val navController = rememberNavController()
        val profileViewModel = ProfileViewModel().apply {
            userName = "João Silva"
            connectionsCount = 14
            music = "Drake, Arctic Monkeys, Tame Impala"
            movies = "Interstellar, Breaking Bad, One Piece"
            games = "Minecraft, Hollow Knight, Valorant"
        }

        ProfileScreen(
            navController = navController,
            profileViewModel = profileViewModel
        )
    }