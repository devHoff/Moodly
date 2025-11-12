package pt.iade.ei.firstapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.iade.ei.firstapp.ui.auth.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel,            // ✅ receive shared VM
    onSignupClick: () -> Unit
) {
    val loading by authViewModel.loading.collectAsState()
    val error by authViewModel.error.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D004B))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bem-vindo ao Moodly 👋",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                cursorColor = Color(0xFFFFD600),
                focusedIndicatorColor = Color(0xFFFFD600)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                cursorColor = Color(0xFFFFD600),
                focusedIndicatorColor = Color(0xFFFFD600)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (error != null) {
            Text(text = error ?: "", color = Color.Red, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = {
                // Do NOT navigate here. Setting session flips the gate in MainActivity.
                authViewModel.login(email, password) { /* no-op */ }
            },
            enabled = !loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD600),
                contentColor = Color.Black
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(if (loading) "Entrando..." else "Entrar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onSignupClick) {
            Text("Não tens conta? Cria uma aqui!", color = Color.White, fontSize = 16.sp)
        }
    }
}



