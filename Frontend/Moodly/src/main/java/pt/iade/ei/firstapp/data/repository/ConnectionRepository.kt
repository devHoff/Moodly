package pt.iade.ei.firstapp.data.repository

import pt.iade.ei.firstapp.data.remote.ConnectionApi
import pt.iade.ei.firstapp.data.remote.RetrofitClient

class ConnectionRepository {

    private val api: ConnectionApi = RetrofitClient.connectionApi()

    suspend fun discoverUsers(
        userId: Long,
        limit: Int
    ): Result<List<ConnectionApi.UsuarioDTO>> {
        return try {
            val list = api.discoverUsers(userId, limit)
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendConnectionRequest(
        currentUserId: Long,
        targetUserId: Long
    ): Result<ConnectionApi.ConnectionRequestResponse> {
        return try {
            val body = ConnectionApi.ConnectionRequestBody(
                currentUserId = currentUserId,
                targetUserId = targetUserId
            )
            val resp = api.sendConnectionRequest(body)
            Result.success(resp)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun mutualConnections(
        userId: Long
    ): Result<List<ConnectionApi.UsuarioDTO>> {
        return try {
            val list = api.mutualConnections(userId)
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun outgoingConnections(
        userId: Long
    ): Result<List<ConnectionApi.OutgoingConnectionDTO>> {
        return try {
            val list = api.outgoingConnections(userId)
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
