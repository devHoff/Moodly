package pt.iade.ei.firstapp.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
            EventDetalhe()
            }
        }
    }

@Composable
fun EventDetalhe(){
    Row(

        modifier = Modifier
            .padding()
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color(color = 0xFF2F0738)),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
    )
    {
        Row(

            modifier = Modifier
                .padding()
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Right
        )
        {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo"

            )


        }
    }



    Text(text = "Conexões",
        modifier = Modifier
            .padding()
            .fillMaxHeight()
            .fillMaxWidth()
            .offset(x = 130.dp)
            .offset(y = 90.dp),
        fontSize = 25.sp,
        color = Color.White
    )

}
}

@Preview(showBackground = true)
@Composable
fun EventDetalhePreview() {
    EventosDetalhe()
}