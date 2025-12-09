package pt.iade.ei.firstapp.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pt.iade.ei.firstapp.ConnectScreen
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
                            painter = painterResource(id = R.drawable.conexao),
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
                            colorFilter = ColorFilter.tint(Color.Yellow),
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D004B))
                .padding(padding)
        ) {
            ConnectScreen(navController)
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
