package pt.iade.ei.firstapp.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    data class UsuarioDTO(
        val id: Long?,
        val nome: String?,
        val email: String?,
        val fotoPerfil: String?
    )

    data class RegisterRequest(
        val nome: String,
        val email: String,
        val password: String
    )

    data class LoginRequest(
        val email: String,
        val password: String
    )

    @POST("/api/auth/signup")
    suspend fun register(@Body req: RegisterRequest): UsuarioDTO

    @POST("/api/auth/login")
    suspend fun login(@Body req: LoginRequest): UsuarioDTO
}

