package pt.iade.ei.firstapp.data

import pt.iade.ei.firstapp.data.remote.AuthApi

object SessionManager {
    var userId: Long? = null
    var nome: String? = null
    var email: String? = null
    var fotoPerfil: String? = null
    var music: String = ""
    var movies: String = ""
    var games: String = ""

    var connectionsCount: Int = 0

    var connectionsRefreshKey: Long = 0L

    val hiddenChatConnections: MutableSet<Long> = mutableSetOf()

    fun applyUser(user: AuthApi.UsuarioDTO) {
        userId = user.id
        nome = user.nome
        email = user.email
        fotoPerfil = user.fotoPerfil
    }

    fun applyInterests(
        musicList: List<String>,
        moviesList: List<String>,
        gamesList: List<String>
    ) {
        music = musicList.joinToString(", ")
        movies = moviesList.joinToString(", ")
        games = gamesList.joinToString(", ")
    }

    fun clear() {
        userId = null
        nome = null
        email = null
        fotoPerfil = null
        music = ""
        movies = ""
        games = ""
        connectionsCount = 0
        connectionsRefreshKey = 0L
        hiddenChatConnections.clear()
    }
}
