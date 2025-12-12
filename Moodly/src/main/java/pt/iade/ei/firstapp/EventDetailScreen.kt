package pt.iade.ei.firstapp

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    nav: NavController,
    eventId: Long,
    title: String,
    organizer: String,
    date: String,
    local: String,
    desc: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do evento", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF190A1C),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF2D004B)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFF2D004B)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3C0063),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(text = title, fontSize = 22.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Organizador: $organizer")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Data: $date")
                    Spacer(modifier = Modifier.height(4.dp))
                    if (local.isNotBlank())
                        Text(text = "Local: $local")
                    Spacer(modifier = Modifier.height(8.dp))
                    if (desc.isNotBlank())
                        Text(text = desc)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { nav.navigate("eventChat/$eventId/$title") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD600),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Ir para o chat do evento")
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF2D004B)
@Composable
fun EventDetailPreview() {
    val nav = rememberNavController()
    EventDetailScreen(
        nav = nav,
        eventId = 1,
        title = "Festa do João",
        organizer = "João Silva",
        date = "10/12/2025 20:00",
        local = "Lisboa Centro",
        desc = "Venham todos celebrar!"
    )
}


