package pt.iade.ei.firstapp.activities

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
import androidx.compose.ui.unit.sp
import pt.iade.ei.firstapp.R
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme


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

        Text(text = "User.Name",
            modifier = Modifier
                .padding()
                .fillMaxHeight()
                .fillMaxWidth()
                .size(size = 250.dp)

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
            color = Color.White

        )


        

    }

    Image(

        modifier = Modifier.size(size =100.dp).offset(y = 385.dp).offset(x=280.dp),
        painter = painterResource(id = R.drawable.swipe),
        contentDescription = "swipe"
    )

    Image(

        modifier = Modifier.size(size =100.dp).offset(y = 385.dp).offset(x=10.dp),
        painter = painterResource(id = R.drawable.conection),
        contentDescription = "swipe"
    )

    Image(

        modifier = Modifier.size(size =50.dp).offset(y = 495.dp).offset(x=35.dp),
        painter = painterResource(id = R.drawable.musica),
        contentDescription = "swipe"
    )

    Image(

        modifier = Modifier.size(size =50.dp).offset(y = 580.dp).offset(x=35.dp),
        painter = painterResource(id = R.drawable.filme),
        contentDescription = "swipe"
    )

    Image(

        modifier = Modifier.size(size =50.dp).offset(y = 670.dp).offset(x=35.dp),
        painter = painterResource(id = R.drawable.jogo),
        contentDescription = "swipe"
    )

    Text(text = "Jhonny Golden",
        modifier = Modifier
            .padding()
            .fillMaxHeight()
            .fillMaxWidth()
            .offset(x = 105.dp)
            .offset(y = 400.dp),
        fontSize = 25.sp
    )
    Text(text = "Eng.Informática",
        modifier = Modifier
            .padding()
            .fillMaxHeight()
            .fillMaxWidth()
            .offset(x = 105.dp)
            .offset(y = 435.dp),
        fontSize = 25.sp
    )

    Column (
        modifier = Modifier.padding().fillMaxWidth().fillMaxWidth(),
        // verticalArrangement =
        horizontalAlignment = Alignment.CenterHorizontally
    ){
       Text(text = "UnderGroundKing-Kmw",
           modifier = Modifier.padding()
               // .offset(x = (-110).dp)
               .offset(y = 490.dp),
           color = Color.White
       )

        Text(text = "Sem ti-KMW",
            modifier = Modifier.padding()
                //.offset(x = (-110).dp)
                .offset(y = 495.dp),
            color = Color.White
        )

        Text(text = "MrSacaRolhas-Não Compensa",
            modifier = Modifier.padding()
                //.offset(x = (-110).dp)
                .offset(y = 500.dp),
            color = Color.White
        )

        Text(text = "LaCasa de Papel",
            modifier = Modifier.padding()
               // .offset(x = (-110).dp)
                .offset(y = 530.dp),
            color = Color.White
        )

        Text(text = "Prison Break",
            modifier = Modifier.padding()
               // .offset(x = (-110).dp)
                .offset(y = 535.dp),
            color = Color.White
        )

        Text(text = "Breaking Bad",
            modifier = Modifier.padding()
                //.offset(x = (-110).dp)
                .offset(y = 540.dp),
            color = Color.White
        )

        Text(text = "Fortnite",
            modifier = Modifier.padding()
                //.offset(x = (-110).dp)
                .offset(y = 570.dp),
            color = Color.White
        )

        Text(text = "GTA 5",
            modifier = Modifier.padding()
               // .offset(x = (-110).dp)
                .offset(y = 575.dp),
            color = Color.White
        )

        Text(text = "Brawhalla",
            modifier = Modifier.padding()
              //  .offset(x = (-110).dp)
                .offset(y = 580.dp),
            color = Color.White
        )

        
        
    }
}




@Preview(showBackground = true)
@Composable
fun TelaPreview(){
    Tela()
}
