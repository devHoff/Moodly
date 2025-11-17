package pt.iade.ei.firstapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.iade.ei.firstapp.data.repository.ProfileRepository

class ProfileViewModel : ViewModel() {



    private val repo = ProfileRepository()

    var userName: String = ""
    var connectionsCount: Int = 0

    var profileImageUrl: String? = null
    var music: String = ""
    var movies: String = ""
    var games: String = ""

    private val _updating = MutableStateFlow(false)
    val updating: StateFlow<Boolean> = _updating

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun update(userId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _updating.value = true
            _error.value = null
            try {
                // split comma-separated input into lists
                val musicList  = music.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                val moviesList = movies.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                val gamesList  = games.split(",").map { it.trim() }.filter { it.isNotEmpty() }

                // call repo; throws on non-2xx
                repo.updateProfile(
                    userId = userId,
                    fotoPerfil = profileImageUrl,
                    music = musicList,
                    movies = moviesList,
                    games = gamesList
                )

                // success — keep our local values (server body ignored)
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message ?: "Falha ao atualizar perfil"
            } finally {
                _updating.value = false
            }
        }
    }
}





