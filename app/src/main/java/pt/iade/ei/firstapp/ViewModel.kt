package pt.iade.ei.firstapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    var userName by mutableStateOf("User Name")
    var profileImageUrl by mutableStateOf<String?>(null)
    var connectionsCount by mutableStateOf(0)
    var music by mutableStateOf("No music preferences")
    var movies by mutableStateOf("No movie preferences")
    var games by mutableStateOf("No game preferences")
}