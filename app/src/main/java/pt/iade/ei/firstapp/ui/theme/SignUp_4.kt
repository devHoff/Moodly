package pt.iade.ei.firstapp.ui.theme

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.ei.firstapp.R
import pt.iade.ei.firstapp.ui.theme.ui.theme.ui.theme.FirstAppTheme

class SignUp_4 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAppTheme {
SextaTela()
            }
        }
    }
}

@Composable
fun SextaTela(){



    Row (

        modifier = Modifier
            .padding()
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color(color = 0xFF2F0738)),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
    )
    {
    }


    Row (

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


    Row (

        modifier = Modifier
            .padding()
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            modifier = Modifier
                .size(size = 150.dp)
                .offset(y=(-170).dp),

            painter = painterResource(id = R.drawable.perfil_logo),
            contentDescription = "logo"

        )
    }

    Column(

        modifier = Modifier
            .padding()
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {
        Image(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.barra_roxa),
            contentDescription = "BAARRA ROXA"

        )

        Image(
            modifier = Modifier
                .offset(y =(30).dp),
            painter = painterResource(id = R.drawable.barra_roxa),
            contentDescription = "BAARRA ROXA"

        )
        Image(
            modifier = Modifier
                .offset(y =(60).dp),
            painter = painterResource(id = R.drawable.barra_roxa),
            contentDescription = "BAARRA ROXA"

        )

        Image(
            modifier = Modifier
                .offset(y =(150).dp),
            painter = painterResource(id = R.drawable.barra),
            contentDescription = "BAARRA ROXA"

        )
    }

    Column (
        modifier = Modifier.padding().fillMaxHeight().fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            modifier = Modifier.
            offset(y = (-20).dp)
                .offset(x=(-80).dp),
            text = "Filme 1"

        )
        Text(
            modifier = Modifier.
            offset(y = (21).dp)
                .offset(x=(-77).dp),
            text = "Filme 2"

        )
        Text(
            modifier = Modifier.
            offset(y = 65.dp)
                .offset(x=(-80).dp),
            text = "Filme 3"

        )

        Text(
            modifier = Modifier.
            offset(y = (165).dp)
                .offset(x=(-5).dp),
            text = "Seguinte"

        )

    }



}

@Preview(showBackground = true)
@Composable
fun SextaTelaPreview(){
    pt.iade.ei.firstapp.ui.theme.ui.theme.FirstAppTheme {
    }
    SextaTela()
}