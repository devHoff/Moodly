package pt.iade.ei.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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

                    NavHost(navController = navController, startDestination = "SplashScreen") {

                        composable("Login") {
                            LoginScreen(
                                navController = navController,
                                onSignupClick = { navController.navigate("home") }
                            )
                        }

                        composable("home") {
                            Tela(navController)
                        }
                        composable("SplashScreen") {
                            SplashScreen(navController)
                        }
                        composable("profile") {
                            val profileViewModel: ProfileViewModel = viewModel()
                            ProfileScreen(
                                navController = navController,
                                profileViewModel = profileViewModel
                            )
                        }

                        composable("chats") {
                            ChatsScreen(navController)
                        }
                        composable("cone") {
                            Conex(navController)
                        }
                        composable("eve") {
                            Even(navController)
                        }
                        composable(route= "edit") {
                            val profileViewModel: ProfileViewModel = viewModel()
                            EditProfileScreen(
                                navController = navController,
                                profileViewModel = profileViewModel
                            )
                        }

                        composable("Sign") {
                            SignupScreen(
                                navController = navController,
                                onSignupClick = { _, _, _ -> },
                                onLoginClick = { navController.navigate("home")}

                            )
                        }

                        composable(route= "Segui") {
                            val profileViewModel: ProfileViewModel = viewModel()
                            Select(
                                navController = navController,
                                profileViewModel = profileViewModel
                            )
                        }

                    }
                }
            }
        }
    }
}