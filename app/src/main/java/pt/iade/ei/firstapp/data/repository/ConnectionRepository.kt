package pt.iade.ei.firstapp.data.repository

import pt.iade.ei.firstapp.data.remote.ConnectionApi
import pt.iade.ei.firstapp.data.remote.RetrofitClient

class ConnectionRepository {

    private val api: ConnectionApi = RetrofitClient.connectionApi()

    suspend fun discoverUsers(
        currentUserId: Long,
        limit: Int = 20
    ): Result<List<ConnectionApi.UsuarioDTO>> {
        return try {
            val list = api.discoverUsers(currentUserId, limit)
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
            val resp = api.sendConnectionRequest(
                ConnectionApi.ConnectionRequestBody(
                    currentUserId = currentUserId,
                    targetUserId = targetUserId
                )
            )
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

    suspend fun pendingConnections(
        userId: Long
    ): Result<List<ConnectionApi.PendingConnectionDTO>> {
        return try {
            val list = api.pendingConnections(userId)
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


