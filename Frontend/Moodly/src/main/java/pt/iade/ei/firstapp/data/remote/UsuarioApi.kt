package pt.iade.ei.firstapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface UsuarioApi {

    data class InteresseDTO(
        val tipo: String?,
        val nome: String?
    )

    data class UsuarioDetalheDTO(
        val id: Long,
        val nome: String?,
        val email: String?,
        val fotoPerfil: String?,
        val interesses: List<InteresseDTO>?
    )

    @GET("/api/usuarios/{id}")
    suspend fun getUsuarioById(
        @Path("id") id: Long
    ): UsuarioDetalheDTO
}
