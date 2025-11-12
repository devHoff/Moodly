package pt.iade.ei.firstapp.data.repository

import pt.iade.ei.firstapp.data.remote.ProfileApi
import pt.iade.ei.firstapp.data.remote.RetrofitClient

class ProfileRepository {

    private val api: ProfileApi = RetrofitClient.profileApi()

    suspend fun updateProfile(
        userId: Long,
        fotoPerfil: String?,
        music: List<String>,
        movies: List<String>,
        games: List<String>
    ) {
        val req = ProfileApi.UserProfileUpdateRequest(
            fotoPerfil = fotoPerfil,
            interesses = buildList {
                addAll(music.map { ProfileApi.InterestDTO("musica", it) })
                addAll(movies.map { ProfileApi.InterestDTO("filme", it) })
                addAll(games.map { ProfileApi.InterestDTO("jogo", it) })
            }
        )

        val resp = api.updateProfile(userId = userId, req = req)
        if (!resp.isSuccessful) {
            val msg = resp.errorBody()?.string()?.take(400) ?: "Erro ao atualizar perfil"
            throw Exception("HTTP ${resp.code()}: $msg")
        }
        // No return payload needed; success == updated
    }
}
