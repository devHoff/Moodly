package pt.iade.ei.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.iade.ei.firstapp.activities.Conex
import pt.iade.ei.firstapp.activities.Tela
import pt.iade.ei.firstapp.auth.LoginScreen
import pt.iade.ei.firstapp.auth.SignupScreen
import pt.iade.ei.firstapp.chat.ChatRoomScreen
import pt.iade.ei.firstapp.chat.ChatsScreen
import pt.iade.ei.firstapp.chat.EventChatScreen
import pt.iade.ei.firstapp.conexões.ConnectScreen
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.eventos.CreateEventStep1
import pt.iade.ei.firstapp.eventos.CreateEventStep2
import pt.iade.ei.firstapp.eventos.Even
import pt.iade.ei.firstapp.eventos.EventDetailScreen
import pt.iade.ei.firstapp.perfil.EditProfileScreen
import pt.iade.ei.firstapp.perfil.ProfilePicSelectionScreen
import pt.iade.ei.firstapp.perfil.ProfileScreen
import pt.iade.ei.firstapp.perfil.Select
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme
import java.net.URLDecoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = android.graphics.Color.parseColor("#2D004B")
        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightStatusBars = false

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

        composable(
            route = "chatroom/{connectionId}/{otherUserId}/{otherUserName}"
        ) { backStackEntry ->
            val connectionId = backStackEntry.arguments?.getString("connectionId")?.toLongOrNull() ?: 0L
            val otherUserId = backStackEntry.arguments?.getString("otherUserId")?.toLongOrNull() ?: 0L
            val otherUserName = backStackEntry.arguments?.getString("otherUserName") ?: ""
            ChatRoomScreen(
                navController = navController,
                connectionId = connectionId,
                otherUserName = otherUserName
            )
        }

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

        composable("Feed") { ConnectScreen(navController) }
        composable("SplashScreen") { SplashScreen(navController) }

        composable("createEventStep1") {
            CreateEventStep1(navController = navController)
        }

        composable("createEventStep2/{dataStr}") { backStackEntry ->
            val dataStr = backStackEntry.arguments?.getString("dataStr") ?: ""
            CreateEventStep2(nav = navController, dataStr = dataStr)
        }

        composable("eventChat/{eventId}/{title}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")?.toLongOrNull() ?: 0L
            val title = backStackEntry.arguments?.getString("title") ?: ""
            EventChatScreen(nav = navController, eventId = eventId, title = title)
        }

        composable("eventDetail/{eventId}/{title}/{organizer}/{date}/{local}/{desc}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")?.toLongOrNull() ?: 0L
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val organizer = backStackEntry.arguments?.getString("organizer") ?: ""
            val date = backStackEntry.arguments?.getString("date") ?: ""
            val local = backStackEntry.arguments?.getString("local") ?: ""
            val desc = backStackEntry.arguments?.getString("desc") ?: ""
            EventDetailScreen(
                nav = navController,
                eventId = eventId,
                title = URLDecoder.decode(title, "UTF-8"),
                organizer = URLDecoder.decode(organizer, "UTF-8"),
                date = URLDecoder.decode(date, "UTF-8"),
                local = URLDecoder.decode(local, "UTF-8"),
                desc = URLDecoder.decode(desc, "UTF-8")
            )
        }
    }
}
