package pt.iade.ei.firstapp.data.repository

import pt.iade.ei.firstapp.data.remote.AuthApi
import pt.iade.ei.firstapp.data.remote.RetrofitClient

class AuthRepository {

    private val api: AuthApi = RetrofitClient.authApi()

    suspend fun signup(
        nome: String,
        email: String,
        password: String
    ): Result<AuthApi.UsuarioDTO> {
        return try {
            val user = api.register(
                AuthApi.RegisterRequest(
                    nome = nome,
                    email = email,
                    password = password
                )
            )
            if (user.id != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Falha a registar conta"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<AuthApi.UsuarioDTO> {
        return try {
            val user = api.login(
                AuthApi.LoginRequest(
                    email = email,
                    password = password
                )
            )
            if (user.id != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Credenciais inválidas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
