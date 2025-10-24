package pt.iade.ei.firstapp.ui.theme.ui.theme

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
import pt.iade.ei.firstapp.ui.theme.ui.theme.ui.theme.FirstAppTheme

class conexao : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAppTheme {
                conexao()
            }
        }
    }


        @Composable
        fun conexao() {
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

            for (i in 1..3) {


                Image(
                    modifier = Modifier.size(size =500.dp).offset(y = ((-170)+i*120).dp).fillMaxWidth().fillMaxHeight(),
                    painter = painterResource(id = R.drawable.barraroxagrossa),
                    contentDescription = "barrafina")

                Text(text = "UserName"+i,
                    modifier = Modifier.padding().fillMaxWidth().fillMaxHeight().offset(x=70.dp).offset(y=((60)+i*120).dp),
                    color = Color.White
                )

                Text(text = "Mensagem"+i,
                    modifier = Modifier.padding().fillMaxWidth().fillMaxHeight().offset(x=70.dp).offset(y=((80)+i*120).dp),
                    color = Color.White)
                Image(

                    modifier = Modifier.size(size =50.dp).offset(y = ((60)+i*120).dp).offset(x=10.dp),
                    painter = painterResource(id = R.drawable.fotodeperfil),
                    contentDescription = "foto de perfil")





            }










        }

        @Preview(showBackground = true)
        @Composable
        fun conexaoPreview() {
            FirstAppTheme {
                conexao()
            }
        }
    }