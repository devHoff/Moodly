package pt.iade.ei.firstapp.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApi {

    data class ChatMessageDTO(
        @SerializedName("id") val id: Long?,
        @SerializedName("autorId") val autorId: Long,
        @SerializedName("autorNome") val autorNome: String?,
        @SerializedName("conteudo") val conteudo: String,
        @SerializedName("dataEnvio") val dataEnvio: String?
    )

    data class SendMessageBody(
        @SerializedName("autorId") val autorId: Long,
        @SerializedName("conteudo") val conteudo: String
    )

    data class ConnectionChatDTO(
        @SerializedName("connectionId") val connectionId: Long,
        @SerializedName("otherUserId") val otherUserId: Long,
        @SerializedName("otherUserName") val otherUserName: String?,
        @SerializedName("otherUserPhoto") val otherUserPhoto: String?,
        @SerializedName("lastMessage") val lastMessage: String?,
        @SerializedName("lastMessageTime") val lastMessageTime: String?
    )

    data class EventChatDTO(
        @SerializedName("eventoId") val eventoId: Long,
        @SerializedName("titulo") val titulo: String
    )

    @GET("/api/chats/connection/{connectionId}/messages")
    suspend fun getConnectionMessages(
        @Path("connectionId") connectionId: Long
    ): List<ChatMessageDTO>

    @POST("/api/chats/connection/{connectionId}/send")
    suspend fun sendConnectionMessage(
        @Path("connectionId") connectionId: Long,
        @Body body: SendMessageBody
    ): Response<ChatMessageDTO>

    @GET("/api/chats/event/{eventId}/messages")
    suspend fun getEventMessages(
        @Path("eventId") eventId: Long
    ): List<ChatMessageDTO>

    @POST("/api/chats/event/{eventId}/send")
    suspend fun sendEventMessage(
        @Path("eventId") eventId: Long,
        @Body body: SendMessageBody
    ): Response<ChatMessageDTO>

    @GET("/api/chats/user/{userId}/connections")
    suspend fun connectionChats(
        @Path("userId") userId: Long
    ): List<ConnectionChatDTO>
}
