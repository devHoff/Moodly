package pt.iade.ei.firstapp.activities

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pt.iade.ei.firstapp.LoginScreen
//import androidx.navigation.NavHostController
import pt.iade.ei.firstapp.R
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme


@Composable
fun Tela(navController: NavController) {

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
                        onClick = {
                            navController.navigate("eve")
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
                    },
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.event),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(36.dp)
                        )
                    }


                    IconButton(onClick = { /* ação */ }

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
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(30.dp))
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Moodly Logo",
                tint = Color.Unspecified,
                modifier = Modifier.size(50.dp).align(Alignment.End)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp) //
            ) {

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF3C0063),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(650.dp)
                        .width(400.dp)
                ) {
                    Column(

                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Icon(
                            painter = painterResource(id = R.drawable.foto),
                            contentDescription = "Foto de perfil",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(280.dp),

                            )

                        Text(
                            text = "Jhonny Golden",
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Eng.Informática",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF190A1C),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .height(290.dp)
                                .width(350.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.filme),
                                        contentDescription = "Filme",
                                        tint = Color(0xFFFFD600),
                                        modifier = Modifier.size(50.dp)
                                    )
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy((-4).dp)
                                    ) {
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
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.musica),
                                        contentDescription = "Musica",
                                        tint = Color(0xFFFFD600),
                                        modifier = Modifier.size(50.dp)
                                    )
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy((-4).dp)
                                    ) {
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
                                    }
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.jogo),
                                        contentDescription = "Filme",
                                        tint = Color(0xFFFFD600),
                                        modifier = Modifier.size(50.dp)
                                    )
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy((-4).dp)
                                    ) {
                                        Text(
                                            text = "Fortnite",
                                            color = Color.White,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "R6",
                                            color = Color.White,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "League of Legends",
                                            color = Color.White,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TelaPreview() {
    FirstAppTheme {
        val navController = rememberNavController()
        Tela(navController = navController)
    }
}
