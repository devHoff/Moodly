package pt.iade.ei.firstapp.ui.theme

import android.app.usage.UsageEvents
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.ei.firstapp.R
import pt.iade.ei.firstapp.ui.theme.ui.theme.FirstAppTheme

class EventosDetalhe : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAppTheme {
                Event()
            }
        }
    }

    @Composable
    fun Event() {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2F0738)),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo"
                )
            }
        }

        Text(
            text = "Detalhes",
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = 140.dp, y = 90.dp),
            fontSize = 25.sp,
            color = Color.White
        )



            Column(
                modifier = Modifier
                    .fillMaxHeight().fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally


            ) {

                for(i in 0..4) {
                    Image(
                        painter = painterResource(id = R.drawable.barra_roxa),
                        contentDescription = "barra",
                        modifier = Modifier.offset(y =((-150)+i*50).dp),
                    )

                    Text(
                        text = "Informações sobre o evento",
                        modifier = Modifier.offset(y =((-193)+i*50).dp),
                        
                    )
                }
            }


    }

    @Preview(showBackground = true)
    @Composable
    fun EventPreview() {
        Event()
    }
}

