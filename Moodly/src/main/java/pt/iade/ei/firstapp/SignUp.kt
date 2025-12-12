package pt.iade.ei.firstapp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.data.repository.AuthRepository
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme

@Composable
fun SignupScreen(
    navController: NavController
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val authRepo = remember { AuthRepository() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D004B))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text("Criar conta", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color(0xFFFFD600),
                focusedIndicatorColor = Color(0xFFFFD600),
                unfocusedIndicatorColor = Color.Gray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color(0xFFFFD600),
                focusedIndicatorColor = Color(0xFFFFD600),
                unfocusedIndicatorColor = Color.Gray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color(0xFFFFD600),
                focusedIndicatorColor = Color(0xFFFFD600),
                unfocusedIndicatorColor = Color.Gray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(Modifier.height(16.dp))

        if (error != null) {
            Text(text = error ?: "", color = Color.Red, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = {
                scope.launch {
                    if (nome.isBlank() || email.isBlank() || password.isBlank()) {
                        error = "Preenche todos os campos"
                        return@launch
                    }
                    loading = true
                    error = null

                    val result = authRepo.signup(nome, email, password)
                    loading = false

                    result.onSuccess { user ->
                        SessionManager.userId = user.id
                        SessionManager.nome = user.nome
                        SessionManager.email = user.email
                        SessionManager.fotoPerfil = user.fotoPerfil

                        navController.navigate("IntToPic") {
                            popUpTo("login") { inclusive = false }
                        }
                    }.onFailure { e ->
                        error = e.message ?: "Falha a registar conta"
                    }
                }
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
            Text(
                if (loading) "A criar..." else "Registar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(12.dp))
        TextButton(onClick = { navController.navigate("login") }) {
            Text("Já tens conta? Inicia sessão", color = Color.White)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    FirstAppTheme {
        val navController = rememberNavController()
        SignupScreen(navController = navController)
    }
}

