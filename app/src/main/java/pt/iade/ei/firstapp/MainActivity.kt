package pt.iade.ei.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.iade.ei.firstapp.activities.Tela
// import pt.iade.ei.firstapp.activities.TelaInicial
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController, startDestination = "SplashScreen",
                ) {
                    composable("Login") {
                        LoginScreen(navController = navController)
                    }

                    composable(route = "home") {
                       // Tela(navController = navController)
                    }

                    composable(route = "SplashScreen") {
                        Splash(navController = navController)

                    }
                }
            }
        }
    }
}