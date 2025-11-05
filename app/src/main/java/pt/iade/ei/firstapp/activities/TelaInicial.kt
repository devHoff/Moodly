package pt.iade.ei.firstapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.navigation.NavHostController
import pt.iade.ei.firstapp.R
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme



@Composable
fun Tela() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D004B))
            .padding(16.dp)
    ) {
        // Logo no topo
        Image(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
        )
        Spacer(modifier = Modifier.height(16.dp))
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
                    .height(700.dp)
                    .width(400.dp)
            ) {
                Column (

                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,

                ){
                    Icon(
                        painter = painterResource(id = R.drawable.foto),
                        contentDescription = "Foto de perfil",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(280.dp),

                        )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Jhonny Golden",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
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
                            .height(320.dp)
                            .width(350.dp)
                    ) {





                    }


                }


            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun TelaPreview(){
    Tela()
}
