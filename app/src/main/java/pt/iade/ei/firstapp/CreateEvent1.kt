package pt.iade.ei.firstapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventStep1(navController: NavController) {
    var titulo by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var local by remember { mutableStateOf("") }
    var dataTexto by remember { mutableStateOf("") }
    var horaTexto by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Criar evento", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF190A1C),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF2D004B)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFF2D004B)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3C0063),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Detalhes do evento",
                        fontSize = 18.sp,
                        color = Color.White
                    )

                    if (error != null) {
                        Text(
                            text = error ?: "",
                            color = Color.Red,
                            fontSize = 13.sp
                        )
                    }

                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Título") },
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

                    OutlinedTextField(
                        value = desc,
                        onValueChange = { desc = it },
                        label = { Text("Descrição") },
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

                    OutlinedTextField(
                        value = local,
                        onValueChange = { local = it },
                        label = { Text("Local") },
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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = dataTexto,
                            onValueChange = { dataTexto = it },
                            label = { Text("Data (dd/mm/aaaa)") },
                            modifier = Modifier.weight(1f),
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

                        OutlinedTextField(
                            value = horaTexto,
                            onValueChange = { horaTexto = it },
                            label = { Text("Hora (hh:mm)") },
                            modifier = Modifier.weight(1f),
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
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        TextButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancelar", color = Color.White)
                        }

                        Button(
                            onClick = {
                                error = null

                                if (titulo.isBlank()) {
                                    error = "Escreve um título para o evento."
                                    return@Button
                                }

                                if (dataTexto.isBlank() || horaTexto.isBlank()) {
                                    error = "Preenche a data e a hora do evento."
                                    return@Button
                                }

                                val isoDateTime = try {
                                    val dp = dataTexto.trim().split("/")
                                    val tp = horaTexto.trim().split(":")
                                    val dia = dp[0].toInt()
                                    val mes = dp[1].toInt()
                                    val ano = dp[2].toInt()
                                    val hora = tp[0].toInt()
                                    val minuto = tp[1].toInt()
                                    "%04d-%02d-%02dT%02d:%02d:00".format(
                                        ano, mes, dia, hora, minuto
                                    )
                                } catch (e: Exception) {
                                    null
                                }

                                if (isoDateTime == null) {
                                    error = "Data ou hora inválida. Usa o formato dd/mm/aaaa e hh:mm."
                                    return@Button
                                }

                                val encoded = listOf(
                                    titulo,
                                    desc,
                                    local,
                                    isoDateTime
                                ).joinToString("|")

                                navController.navigate("createEventStep2/$encoded")
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFD600),
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Próximo")
                        }
                    }
                }
            }
        }
    }
}
