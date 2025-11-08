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
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme


@Composable
fun ProfileScreen(
    navController: NavController,
    userName: String = "Nome do Usuário",
    profileImageUrl: String? = null,
    connectionsCount: Int = 0,
    music: String = "",
    movies: String = "",
    games: String = "",
    onEditClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
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
                        onClick = {navController.navigate("eve")
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        )

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.oi),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(36.dp)
                        )

                    }

                    IconButton(onClick = {
                        navController.navigate("cone")
                    }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.event),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(36.dp)
                        )
                    }


                    IconButton(onClick = {

                    }

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mood),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(36.dp)
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate("chats")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Message,
                            contentDescription = "Mensagens",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate("profile")
                    }) {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Perfil",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                }


            }
        }
    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D004B))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🔹 Top bar (settings left, Moodly icon right)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onSettingsClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.definicoes),
                        contentDescription = "Settings",
                        tint = Color(0xFFFFD600),
                        modifier = Modifier.size(65.dp)
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Moodly Logo",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(65.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            // 🔹 Profile Picture (rectangle)
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF3C0063)),
                contentAlignment = Alignment.Center
            ) {
                if (profileImageUrl != null) {
                    AsyncImage(
                        model = profileImageUrl,
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

            Spacer(modifier = Modifier.height(15.dp))


            Text(
                text = userName,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = "$connectionsCount Conexões",
                color = Color(0xFFFFD600),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(1.dp))


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF3C0063), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Interesses:",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Stranger Things",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Lupin",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "La Casa de Papel",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Manifest",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Lupin",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "O Homem do Futuro",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Kelson-Uk",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Lil Baby- We Paid",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Não Compensa",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

            }

            Spacer(modifier = Modifier.height(40.dp))

            // 🔹 Edit Button
            Button(
                onClick = onEditClick,
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
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    FirstAppTheme {
        val navController = rememberNavController()
        ProfileScreen(navController = navController,
            userName = "João Silva",
            profileImageUrl = null,
            connectionsCount = 14,
            music = "Drake, Arctic Monkeys, Tame Impala",
            movies = "Interstellar, Breaking Bad, One Piece",
            games = "Minecraft, Hollow Knight, Valorant",
            onEditClick = {},
            onSettingsClick = {})
    }
}