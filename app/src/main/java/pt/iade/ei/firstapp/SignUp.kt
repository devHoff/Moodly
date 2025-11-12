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
fun SignupScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val loading by authViewModel.loading.collectAsState()
    val error by authViewModel.error.collectAsState()

    var nome by remember { mutableStateOf("") }
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
        Text("Criar conta", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = nome, onValueChange = { nome = it }, label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White, unfocusedLabelColor = Color.LightGray,
                cursorColor = Color(0xFFFFD600), focusedIndicatorColor = Color(0xFFFFD600)
            )
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = email, onValueChange = { email = it }, label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White, unfocusedLabelColor = Color.LightGray,
                cursorColor = Color(0xFFFFD600), focusedIndicatorColor = Color(0xFFFFD600)
            )
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = password, onValueChange = { password = it }, label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White, unfocusedLabelColor = Color.LightGray,
                cursorColor = Color(0xFFFFD600), focusedIndicatorColor = Color(0xFFFFD600)
            )
        )

        Spacer(Modifier.height(12.dp))

        if (error != null) {
            Text(text = error ?: "", color = Color.Red, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = { authViewModel.signup(nome, email, password) { /* AuthGate flips */ } },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600), contentColor = Color.Black)
        ) {
            Text(if (loading) "A criar..." else "Criar conta", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(8.dp))
        TextButton(onClick = { navController.popBackStack() }) {
            Text("Já tens conta? Inicia sessão", color = Color.White)
        }
    }
}

