package pt.iade.ei.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import pt.iade.ei.firstapp.activities.Conex
import pt.iade.ei.firstapp.activities.Even
import pt.iade.ei.firstapp.activities.Tela
import pt.iade.ei.firstapp.ui.auth.AuthViewModel
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstAppTheme {
                val navController = rememberNavController()
                val authVm: AuthViewModel = viewModel()
                val session = authVm.session.collectAsStateWithLifecycle().value
                val isAuthenticated = session.userId != null

                // Observa autenticação e redireciona automaticamente


                AppNavigation(navController, authVm)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, authVm: AuthViewModel) {
    val session = authVm.session.collectAsStateWithLifecycle().value
    val userId = session.userId ?: 0L

    NavHost(navController, startDestination = "login") {

        // --- Autenticação ---
        composable("login") {
            LoginScreen(
                navController = navController,
                authViewModel = authVm,
                onSignupClick = { navController.navigate("signup") }
            )
        }
        composable("signup") {
            SignupScreen(navController = navController, authViewModel = authVm,)
        }


        composable("home") { Tela(navController) }

        composable("profile") {
            val profileViewModel: ProfileViewModel = viewModel()
            ProfileScreen(navController = navController, profileViewModel = profileViewModel)
        }

        composable("edit") {
            EditProfileScreen(
                userId = userId,
                onCancel = { navController.popBackStack() },
                onSaved = { navController.navigate("profile") }
            )
        }

        composable("cone") {
            Conex(navController)
        }


        composable("chats") { ChatsScreen(navController) }

        composable("eve") { Even(navController) }
        composable("IntToPic") {
            val profileViewModel: ProfileViewModel = viewModel()
            Select(navController = navController, profileViewModel = profileViewModel)
        }
        composable("Pic") {
            ProfilePicSelectionScreen(
                navController = navController,
                onNextClick = { uri ->
                    navController.navigate("home")
                },
                onSkipClick = {
                    navController.navigate("home")
                }
            )
        }


    }
}
