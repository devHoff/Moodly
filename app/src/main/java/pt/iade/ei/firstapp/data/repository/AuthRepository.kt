package pt.iade.ei.firstapp.data.repository

import pt.iade.ei.firstapp.data.remote.AuthApi
import pt.iade.ei.firstapp.data.remote.RetrofitClient

class AuthRepository {

    private val api: AuthApi = RetrofitClient.authApi()

    suspend fun signup(nome: String, email: String, password: String): AuthApi.AuthResponse {
        // Prefer the /api/auth/signup contract
        return try {
            api.signupAuth(AuthApi.AuthRequest(nome = nome, email = email, password = password))
        } catch (e: Exception) {
            // Fallback to /api/usuarios/register (returns UsuarioDTO)
            val u = api.signupUsuarios(
                AuthApi.UsuarioDTO(
                    id = null,
                    nome = nome,
                    email = email,
                    senhaHash = password,
                    fotoPerfil = null
                )
            )
            AuthApi.AuthResponse(
                ok = (u.id != null),
                message = if (u.id != null) "Conta criada com sucesso" else "Falha ao criar conta",
                userId = u.id,
                nome = u.nome,
                email = u.email,
                fotoPerfil = u.fotoPerfil
            )
        }
    }

    suspend fun login(email: String, password: String): AuthApi.AuthResponse {
        // Prefer the /api/auth/login JSON
        return try {
            api.loginAuth(AuthApi.AuthRequest(email = email, password = password))
        } catch (e: Exception) {
            // Fallback to /api/usuarios/login (returns UsuarioDTO)
            val u = api.loginUsuarios(
                AuthApi.UsuariosLoginRequest(email = email, senhaHash = password)
            )
            if (u.id == null) {
                AuthApi.AuthResponse(
                    ok = false, message = "Credenciais inválidas",
                    userId = null, nome = null, email = null, fotoPerfil = null
                )
            } else {
                AuthApi.AuthResponse(
                    ok = true, message = "Login bem-sucedido",
                    userId = u.id, nome = u.nome, email = u.email, fotoPerfil = u.fotoPerfil
                )
            }
        }
    }
}
