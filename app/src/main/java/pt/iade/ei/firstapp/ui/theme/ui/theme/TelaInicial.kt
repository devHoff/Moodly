package pt.iade.ei.firstapp.ui.theme.ui.theme

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
import pt.iade.ei.firstapp.R
import pt.iade.ei.firstapp.ui.theme.ui.theme.ui.theme.FirstAppTheme

class TelaInicial : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAppTheme {
            Tela()
                }
            }
        }
}
@Composable

fun Tela() {


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


        Row(

            modifier = Modifier
                .padding()
                .fillMaxHeight()
                .fillMaxWidth(),
        )
        {
            Image(

                modifier = Modifier.size(size = 70.dp),
                painter = painterResource(id = R.drawable.definicoes),
                contentDescription = "Def"

            )

    }



    Row(

        modifier = Modifier
            .padding()
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {


        Image(

            modifier = Modifier.size(size = 650.dp).offset(y = 30.dp),
            painter = painterResource(id = R.drawable.segfundo),
            contentDescription = "Def"
        )



    }

    Image(
        modifier = Modifier.padding().size(size = 350.dp).offset(x = 21.dp).offset(y = (450).dp),
        painter = painterResource(R.drawable.fundopreto),
        contentDescription = "ok"
    )

    Image(

        modifier = Modifier.size(size = 250.dp).offset(y = 150.dp).offset(x=65.dp),
        painter = painterResource(id = R.drawable.foto),
        contentDescription = "Def"
    )


    Column (

    ) {
        Text(text = "Encontrar um amigo",
            modifier = Modifier
                .padding()
                .fillMaxHeight()
                .fillMaxWidth()
                .offset(x = 135.dp)
                .offset(y = 100.dp),

        )
    }

}




@Preview(showBackground = true)
@Composable
fun TelaPreview(){
    Tela()
}
