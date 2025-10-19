package pt.iade.ei.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme
import androidx.compose.ui.unit.sp

class Login: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        FirstAppTheme {
            SegundaTela()
        }
        }
    }
}


@Composable
fun SegundaTela(){

    Scaffold (
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(75.dp),
                containerColor = Color(0xFFFFFFFF)
            ){}
        }
    ){ innerPadding ->

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF2F0738))
        ){

            Image(
            modifier = Modifier
                .offset(y=290.dp),
                painter = painterResource(id = R.drawable.login),
                contentDescription = "Login"

                )


            Image(
                modifier = Modifier
                    .offset(y=320.dp),
                painter = painterResource(id = R.drawable.signup),
                contentDescription = "SignUp"

            )



            // Logo
            Image(
                modifier = Modifier
                    .size(150.dp)
                    .offset(y = (-170).dp),
                painter = painterResource(R.drawable.perfil_logo),
                contentDescription = "PERFIL LOGO"
            )




            // Texto "Email"
            Text(
                text = "Email",
                fontSize = 15.sp,
                color = Color.White,
                modifier = Modifier
                    .offset(y = (-50).dp)
                    .offset(x =(-71).dp )
            )

            // Texto "PassWord
            Text(
                text = "Passeword",
                fontSize = 15.sp,
                color = Color.White,
                modifier = Modifier
                    .offset(x =(-60).dp )
                )


            // Mostra a primeira barra
            Image(
                painter = painterResource(R.drawable.barra),
                contentDescription = "Barra",
                modifier = Modifier
                    .offset(y = (0).dp)
                )

            // Mostra a Segunda barra
            Image(
                modifier = Modifier
                    .offset(y = (-100).dp),
                painter = painterResource(R.drawable.barra),
                contentDescription = "Barra"
            )

        }
    }
}


@Preview(showBackground = true)
@Composable

fun SegundaTelaPreview(){
    SegundaTela()
}

