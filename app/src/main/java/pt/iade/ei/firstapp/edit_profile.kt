package pt.iade.ei.firstapp

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(profileViewModel.profileImageUrl as Uri?) }
    var music by remember { mutableStateOf(profileViewModel.music) }
    var movies by remember { mutableStateOf(profileViewModel.movies) }
    var games by remember { mutableStateOf(profileViewModel.games) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Editar Perfil",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF2D004B)
                )
            )
        },
        bottomBar = {
            // Bottom buttons that stay fixed at the bottom
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2D004B)),
                color = Color(0xFF2D004B)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            // Update the ViewModel with new data
                            profileViewModel.profileImageUrl = selectedImageUri as String?
                            profileViewModel.music = music
                            profileViewModel.movies = movies
                            profileViewModel.games = games

                            // Navigate back to profile
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFD600),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Guardar", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D004B))
                .padding(16.dp)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()) // Add scrolling
        ) {
            // Profile Picture Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF3C0063))
                        .clickable { imagePickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageUri != null) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize().clip(CircleShape)
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = "Pick Image",
                            tint = Color(0xFFFFD600),
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }

                Text(
                    text = "Carregar Foto",
                    color = Color(0xFFFFD600),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Interest Inputs Section
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                InterestInputCard(
                    iconRes = R.drawable.music,
                    label = "Músicas",
                    value = music,
                    onValueChange = { music = it },
                    placeholder = "Ex: Arctic Monkeys, Drake, Billie Eilish"
                )

                Spacer(modifier = Modifier.height(16.dp))

                InterestInputCard(
                    iconRes = R.drawable.movies,
                    label = "Filmes e Séries",
                    value = movies,
                    onValueChange = { movies = it },
                    placeholder = "Ex: Breaking Bad, Interstellar, One Piece"
                )

                Spacer(modifier = Modifier.height(16.dp))

                InterestInputCard(
                    iconRes = R.drawable.games,
                    label = "Jogos",
                    value = games,
                    onValueChange = { games = it },
                    placeholder = "Ex: CS:GO, Minecraft, Hollow Knight"
                )
            }

            // Add extra space at the bottom to account for the fixed buttons
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun InterestInputCard(
    iconRes: Int,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF3C0063)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    color = Color(0xFFFFD600),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF2D004B),
                    unfocusedContainerColor = Color(0xFF2D004B),
                    focusedIndicatorColor = Color(0xFFFFD600),
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color(0xFFFFD600),
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = false,
                maxLines = 3
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, backgroundColor = 0xFF2D004B)
@Composable
fun EditProfileScreenPreview() {
    val navController = rememberNavController()
    val profileViewModel = ProfileViewModel()
    EditProfileScreen(
        navController = navController,
        profileViewModel = profileViewModel
    )
}