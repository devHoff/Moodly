package pt.iade.ei.firstapp

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable


fun Splash(navController: NavController) {


    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        navController.navigate("Login") {
            popUpTo("SplashScreen") { inclusive = true }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D004B)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    FirstAppTheme {
        val navController = rememberNavController()
        Splash(navController = navController)
    }
}

