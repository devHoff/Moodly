package pt.iade.ei.firstapp.data.repository

import pt.iade.ei.firstapp.data.remote.RetrofitClient
import pt.iade.ei.firstapp.data.remote.UsuarioApi

class UsuarioRepository {

    private val api: UsuarioApi = RetrofitClient.usuarioApi()

    suspend fun getUsuarioDetalhe(id: Long): Result<UsuarioApi.UsuarioDetalheDTO> {
        return try {
            val dto = api.getUsuarioById(id)
            Result.success(dto)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


