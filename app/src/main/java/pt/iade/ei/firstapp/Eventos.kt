package pt.iade.ei.firstapp.activities

import android.R.attr.text
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ControlPoint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import pt.iade.ei.firstapp.R
import pt.iade.ei.firstapp.ui.components.InterestInputCard
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme




@Composable
fun Even(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF190A1C),
                modifier = Modifier.height(75.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate("cone")
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.event),
                            contentDescription = "Logo",
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate("home")
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.mood),
                            contentDescription = "Logo",
                            modifier = Modifier.size(36.dp)
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
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFF2D004B)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo"
            )

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "Eventos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Column {
                for (i in 1..4) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF3C0063),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .height(130.dp)
                            .width(400.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(25.dp),
                    ) {
                        Row(modifier = Modifier.padding(horizontal = 7.dp, vertical = 8.dp)) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "Foto de perfil",
                                tint = Color.White,
                                modifier = Modifier.size(64.dp)
                            )

                            Column {

                                Row (

                                ){
                                    Text(
                                        modifier = Modifier.padding(top = 20.dp),
                                        text = "Organizador : ",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        modifier = Modifier.padding(top = 20.dp),
                                        text = "Dickson",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Box {

                                    Button(
                                        onClick = {

                                        },colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Transparent)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ControlPoint,
                                            contentDescription = "Ícone de mensagem",
                                            tint = Color(0xFFFFD600),
                                            modifier = Modifier.size(45.dp).offset(x = (-15).dp)
                                        )
                                    }
                                    Text(
                                        modifier = Modifier.padding(start = 50.dp, top = 21.dp),
                                        text = "Mais detalhes",
                                        fontSize = 19.sp,
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



@Preview(showBackground = true)
@Composable
fun EvenPreview() {
    FirstAppTheme {
        val navController = rememberNavController()
        Even(navController = navController)
    }
}
