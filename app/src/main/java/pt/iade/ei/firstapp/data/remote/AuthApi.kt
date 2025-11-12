package pt.iade.ei.firstapp.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    // ====== AUTH-style endpoints (/api/auth/*) ======
    data class AuthRequest(
        val nome: String? = null,
        val email: String,
        val password: String
    )

    data class AuthResponse(
        val ok: Boolean,
        val message: String,
        val userId: Long?,
        val nome: String?,
        val email: String?,
        val fotoPerfil: String?
    )

    @POST("/api/auth/signup")
    suspend fun signupAuth(@Body req: AuthRequest): AuthResponse

    @POST("/api/auth/login")
    suspend fun loginAuth(@Body req: AuthRequest): AuthResponse

    // ====== USUARIOS-style endpoints (/api/usuarios/*) ======
    data class UsuariosLoginRequest(
        val email: String,
        // backend may be reading field "senhaHash" or raw password under this name
        @SerializedName(value = "senhaHash", alternate = ["senha_hash"])
        val senhaHash: String
    )

    // Accept both snake_case and camelCase coming from your backend
    data class UsuarioDTO(
        @SerializedName(value = "id", alternate = ["usuar_id"])
        val id: Long?,

        val nome: String?,

        val email: String?,

        @SerializedName(value = "senhaHash", alternate = ["senha_hash"])
        val senhaHash: String?,

        @SerializedName(value = "fotoPerfil", alternate = ["foto_perfil"])
        val fotoPerfil: String?
    )

    @POST("/api/usuarios/register")
    suspend fun signupUsuarios(@Body req: UsuarioDTO): UsuarioDTO

    @POST("/api/usuarios/login")
    suspend fun loginUsuarios(@Body req: UsuariosLoginRequest): UsuarioDTO
}

