package pt.iade.ei.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.MenuProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.iade.ei.firstapp.activities.Conex
import pt.iade.ei.firstapp.activities.Even
import pt.iade.ei.firstapp.activities.Tela
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAppTheme(darkTheme = false) {
                androidx.compose.material3.Surface(
                    color = androidx.compose.ui.graphics.Color.White
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "SplashScreen"
                    ) {
                        composable("Login") { LoginScreen(navController) }
                        composable("home") {  Tela(navController)  }
                        composable("SplashScreen") { Splash(navController) }
                        //composable("EventMoreDetails") { EventMore(navController) }
                        composable("profile") { ProfileScreen(navController) }
                        composable("chats") { ChatsScreen(navController) }
                        composable("cone") { Conex(navController) }
                        composable("eve") { Even(navController) }
                    }
                }
            }
        }
    }
}
