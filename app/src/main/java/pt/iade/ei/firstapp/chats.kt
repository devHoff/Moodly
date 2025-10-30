package pt.iade.ei.firstapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage

data class ChatPreview(
    val id: String,
    val name: String,
    val lastMessage: String,
    val profilePicUrl: String? = null
)

@Composable
fun ChatsScreen(
    chats: List<ChatPreview>,
    onChatClick: (ChatPreview) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D004B))
            .padding(horizontal = 16.dp)
    ) {
        // 🔹 Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left spacer (for centering title)
            Spacer(modifier = Modifier.width(36.dp))

            // Center title
            Text(
                text = "Os teus chats",
                color = Color(0xFFFFD600),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Moodly icon (top right)
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Moodly Logo",
                tint = Color.Unspecified,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔹 List of chats
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(chats) { chat ->
                ChatListItem(chat = chat, onClick = { onChatClick(chat) })
            }
        }
    }
}

@Composable
fun ChatListItem(chat: ChatPreview, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3C0063)),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (chat.profilePicUrl != null) {
                AsyncImage(
                    model = chat.profilePicUrl,
                    contentDescription = chat.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_user_placeholder),
                    contentDescription = "Default Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = chat.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = chat.lastMessage,
                    color = Color.LightGray,
                    fontSize = 13.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF2D004B)
@Composable
fun ChatsScreenPreview() {
    val demoChats = listOf(
        ChatPreview("1", "Maria Santos", "Então, vais ao hangout hoje?", null),
        ChatPreview("2", "Pedro Costa", "Olá tudo bem?", null),
        ChatPreview("3", "Inês Silva", "tu: Boa noite!", null)
    )
    ChatsScreen(chats = demoChats, onChatClick = {})
}
