package pt.iade.ei.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.iade.ei.firstapp.activities.Conex
import pt.iade.ei.firstapp.activities.Even
import pt.iade.ei.firstapp.activities.Tela
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstAppTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "SplashScreen") {

        composable("login") {
            LoginScreen(
                navController = navController,
                onSignupClick = { navController.navigate("signup") }
            )
        }

        composable("signup") {
            SignupScreen(navController = navController)
        }

        composable("home") { Tela(navController) }

        composable("profile") {
            ProfileScreen(navController = navController)
        }

        composable("edit") {
            val userId = SessionManager.userId ?: -1L
            EditProfileScreen(
                userId = userId,
                onCancel = { navController.popBackStack() },
                onSaved = { navController.navigate("profile") }
            )
        }

        composable("cone") { Conex(navController) }
        composable("chats") { ChatsScreen(navController) }
        composable("eve") { Even(navController) }

        composable("IntToPic") {
            Select(navController = navController)
        }

        composable("Pic") {
            ProfilePicSelectionScreen(
                navController = navController,
                onNextClick = { uri ->
                    SessionManager.fotoPerfil = uri?.toString()
                },
                onSkipClick = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("Feed") { FeedScreen(navController) }
        composable("SplashScreen") { SplashScreen(navController) }
    }
}
