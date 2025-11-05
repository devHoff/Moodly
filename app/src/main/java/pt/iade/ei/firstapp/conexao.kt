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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import coil.compose.AsyncImage
import pt.iade.ei.firstapp.R
import pt.iade.ei.firstapp.ui.components.InterestInputCard
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme

class Eventos : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAppTheme {
                Event()
            }

        }
    }
}

@Composable
fun Event() {
    var selectedImageUri by remember { mutableStateOf("") }
    var music by remember { mutableStateOf("") }
    var movies by remember { mutableStateOf("") }
    var games by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D004B))
            .padding(16.dp)
    ) {
        Image(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Conexões",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(24.dp))

        for (i in 0..4) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3C0063),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .height(100.dp)
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
                        Text(text = "Você se conectou com Delma")
                        Box (

                        ){
                            Icon(
                                imageVector = Icons.Default.Message,
                                contentDescription = "Ícone de mensagem",
                                tint = Color(0xFFFFD600),
                                modifier = Modifier.size(50.dp)
                            )


                            Text(
                                modifier = Modifier.padding(start = 50.dp, top = 10.dp),
                                text = "Clique para iniciar uma conversa",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun EventPreview() {
    Event()
}