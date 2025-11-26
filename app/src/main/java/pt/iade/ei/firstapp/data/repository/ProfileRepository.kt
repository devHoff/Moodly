package pt.iade.ei.firstapp.data.repository

import pt.iade.ei.firstapp.data.remote.ProfileApi
import pt.iade.ei.firstapp.data.remote.RetrofitClient

class ProfileRepository {

    private val api: ProfileApi = RetrofitClient.profileApi()

    suspend fun getProfile(userId: Long): ProfileApi.UserProfileResponse {
        val resp = api.getProfile(userId)
        if (!resp.isSuccessful) {
            val msg = resp.errorBody()?.string()?.take(400) ?: "Erro ao carregar perfil"
            throw Exception(msg)
        }
        return resp.body() ?: throw Exception("Resposta de perfil vazia")
    }

    suspend fun updateProfile(
        userId: Long,
        fotoPerfil: String?,
        music: List<String>,
        movies: List<String>,
        games: List<String>
    ) {
        val interesses = buildList {
            addAll(music.map { ProfileApi.InterestDTO(tipo = "musica", nome = it) })
            addAll(movies.map { ProfileApi.InterestDTO(tipo = "filme", nome = it) })
            addAll(games.map { ProfileApi.InterestDTO(tipo = "jogo", nome = it) })
        }

        val req = ProfileApi.UserProfileUpdateRequest(
            fotoPerfil = fotoPerfil,
            interesses = interesses
        )

        val resp = api.updateProfile(userId, req)
        if (!resp.isSuccessful) {
            val msg = resp.errorBody()?.string()?.take(400) ?: "Erro ao atualizar perfil"
            throw Exception(msg)
        }
    }
}
