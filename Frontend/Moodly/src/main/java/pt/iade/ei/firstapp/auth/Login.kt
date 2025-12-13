package pt.iade.ei.firstapp.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import pt.iade.ei.firstapp.R
import pt.iade.ei.firstapp.data.SessionManager
import pt.iade.ei.firstapp.data.repository.AuthRepository
import pt.iade.ei.firstapp.data.repository.UsuarioRepository
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme

@Composable
fun LoginScreen(
    navController: NavController,
    onSignupClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val authRepo = remember { AuthRepository() }
    val usuarioRepo = remember { UsuarioRepository() }

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

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            singleLine = true,
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

        Spacer(modifier = Modifier.height(16.dp))

        if (error != null) {
            Text(
                text = error ?: "",
                color = Color.Red,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                scope.launch {
                    if (email.isBlank() || password.isBlank()) {
                        error = "Preenche email e senha"
                        return@launch
                    }
                    loading = true
                    error = null

                    val result = authRepo.login(email, password)

                    result.onSuccess { user ->
                        SessionManager.applyUser(user)

                        val id = user.id
                        if (id != null) {
                            val detalheRes = usuarioRepo.getUsuarioDetalhe(id)
                            detalheRes.onSuccess { detalhe ->
                                val musicList = mutableListOf<String>()
                                val moviesList = mutableListOf<String>()
                                val gamesList = mutableListOf<String>()

                                detalhe.interesses.orEmpty().forEach { i ->
                                    val tipo = i.tipo?.lowercase() ?: ""
                                    val nome = i.nome ?: ""
                                    if (nome.isBlank()) return@forEach
                                    when {
                                        "musica" in tipo || "music" in tipo -> musicList.add(nome)
                                        "filme" in tipo || "film" in tipo || "serie" in tipo || "series" in tipo -> moviesList.add(nome)
                                        "jogo" in tipo || "game" in tipo -> gamesList.add(nome)
                                    }
                                }

                                SessionManager.applyInterests(
                                    musicList = musicList,
                                    moviesList = moviesList,
                                    gamesList = gamesList
                                )
                            }
                        }

                        loading = false
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }.onFailure { e ->
                        loading = false
                        error = e.message ?: "Credenciais inválidas"
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
                if (loading) "Entrando..." else "Entrar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onSignupClick) {
            Text("Não tens conta? Cria uma aqui!", color = Color.White, fontSize = 16.sp)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    FirstAppTheme {
        val navController = rememberNavController()
        LoginScreen(
            navController = navController,
            onSignupClick = {}
        )
    }
}
