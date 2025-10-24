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

class Eventos : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAppTheme {
                Eventos()
            }

        }
    }
    @Composable
    fun Eventos() {
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

        Text(text = "Eventos",
            modifier = Modifier
                .padding()
                .fillMaxHeight()
                .fillMaxWidth()
                .offset(x = 140.dp)
                .offset(y = 90.dp),
            fontSize = 25.sp,
            color = Color.White



        )

        for (i in 1..3) {


            Image(
                modifier = Modifier.size(size =400.dp).offset(y = ((-170)+i*200).dp).fillMaxWidth().fillMaxHeight(),
                painter = painterResource(id = R.drawable.roxa),
                contentDescription = "barrafina")

            Image(
                modifier = Modifier.size(size =65.dp).offset(y = ((25)+i*200).dp).fillMaxWidth().fillMaxHeight(),
                painter = painterResource(id = R.drawable.plus),
                contentDescription = "barrafina")

            Text(text = "Organizador:"+i,
                modifier = Modifier.padding().fillMaxWidth().fillMaxHeight().offset(x=70.dp).offset(y=((-40)+i*200).dp),
                color = Color.White
            )

            Text(text = "Tema"+i,
                modifier = Modifier.padding().fillMaxWidth().fillMaxHeight().offset(x=70.dp).offset(y=((-10)+i*200).dp),
                color = Color.White)

            Text(text = "Mais detalhes"+i,
                modifier = Modifier.padding().fillMaxWidth().fillMaxHeight().offset(x=65.dp).offset(y=((31)+i*200).dp),
                color = Color.White)

            Image(

                modifier = Modifier.size(size =50.dp).offset(y = ((-40)+i*200).dp).offset(x=10.dp),
                painter = painterResource(id = R.drawable.fotodeperfil),
                contentDescription = "foto de perfil")





        }




    }




    @Preview(showBackground = true)
    @Composable
    fun EventosPreview() {
        FirstAppTheme {
            Eventos()
        }
    }
}