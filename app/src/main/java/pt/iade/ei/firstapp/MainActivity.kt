package pt.iade.ei.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

                if (isAuthenticated) {
                    AppNav(navController = navController, authVm = authVm)
                } else {
                    AuthNav(navController = navController, authVm = authVm)
                }
            }
        }
    }
}

/** Auth graph */
@Composable
private fun AuthNav(
    navController: NavHostController,
    authVm: AuthViewModel
) {
    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") {
            LoginScreen(
                navController = navController,
                authViewModel = authVm,
                onSignupClick = { navController.navigate("Sign") }
            )
        }
        composable("Sign") {
            SignupScreen(
                navController = navController,
                authViewModel = authVm
            )
        }
    }
}

/** App graph */
@Composable
private fun AppNav(
    navController: NavHostController,
    authVm: AuthViewModel
) {
    val session = authVm.session.collectAsStateWithLifecycle().value
    val userId = session.userId ?: 0L  // App graph only shows when logged in, so userId should exist

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { Tela(navController) }
        composable("profile") { ProfileScreen(navController = navController) }
        composable("edit") {
            EditProfileScreen(
                userId = userId,                               // ✅ pass userId to persist
                onCancel = { navController.popBackStack() },
                onSaved  = { navController.navigate("profile") }
            )
        }
    }
}
