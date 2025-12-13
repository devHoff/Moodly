package pt.iade.ei.firstapp.data.repository

import pt.iade.ei.firstapp.data.remote.ChatApi
import pt.iade.ei.firstapp.data.remote.RetrofitClient

class ChatRepository {

    private val api: ChatApi = RetrofitClient.chatApi()

    suspend fun getConnectionChats(userId: Long): Result<List<ChatApi.ConnectionChatDTO>> {
        return try {
            Result.success(api.connectionChats(userId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getConnectionMessages(connectionId: Long): Result<List<ChatApi.ChatMessageDTO>> {
        return try {
            Result.success(api.getConnectionMessages(connectionId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendConnectionMessage(
        connectionId: Long,
        autorId: Long,
        conteudo: String
    ): Result<ChatApi.ChatMessageDTO> {
        return try {
            val body = ChatApi.SendMessageBody(autorId = autorId, conteudo = conteudo)
            val resp = api.sendConnectionMessage(connectionId, body)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()!!)
            } else {
                Result.failure(RuntimeException("Erro ao enviar mensagem: ${resp.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getEventMessages(eventId: Long): Result<List<ChatApi.ChatMessageDTO>> {
        return try {
            Result.success(api.getEventMessages(eventId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendEventMessage(
        eventId: Long,
        autorId: Long,
        conteudo: String
    ): Result<ChatApi.ChatMessageDTO> {
        return try {
            val body = ChatApi.SendMessageBody(autorId = autorId, conteudo = conteudo)
            val resp = api.sendEventMessage(eventId, body)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()!!)
            } else {
                Result.failure(RuntimeException("Erro ao enviar mensagem: ${resp.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
