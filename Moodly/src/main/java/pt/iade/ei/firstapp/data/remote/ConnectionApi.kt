package pt.iade.ei.firstapp.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ConnectionApi {

    data class UsuarioDTO(
        @SerializedName("id") val id: Long,
        @SerializedName("nome") val nome: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("fotoPerfil") val fotoPerfil: String?
    )

    data class ConnectionRequestBody(
        @SerializedName("currentUserId") val currentUserId: Long,
        @SerializedName("targetUserId") val targetUserId: Long
    )

    data class ConnectionRequestResponse(
        @SerializedName("connectionId") val connectionId: Long?,
        @SerializedName("status") val status: String?,
        @SerializedName("mutual") val mutual: Boolean?
    )

    data class PendingConnectionDTO(
        @SerializedName("connectionId") val connectionId: Long,
        @SerializedName("userId") val userId: Long,
        @SerializedName("nome") val nome: String?,
        @SerializedName("fotoPerfil") val fotoPerfil: String?
    )

    data class OutgoingConnectionDTO(
        @SerializedName("connectionId") val connectionId: Long,
        @SerializedName("userId") val userId: Long,
        @SerializedName("nome") val nome: String?,
        @SerializedName("fotoPerfil") val fotoPerfil: String?,
        @SerializedName("mutual") val mutual: Boolean
    )

    @POST("/api/connections/request")
    suspend fun sendConnectionRequest(
        @Body body: ConnectionRequestBody
    ): ConnectionRequestResponse

    @GET("/api/connections/mutual/{userId}")
    suspend fun mutualConnections(
        @Path("userId") userId: Long
    ): List<UsuarioDTO>

    @GET("/api/connections/pending/{userId}")
    suspend fun pendingConnections(
        @Path("userId") userId: Long
    ): List<PendingConnectionDTO>

    @GET("/api/connections/discover/{userId}")
    suspend fun discoverUsers(
        @Path("userId") userId: Long,
        @Query("limit") limit: Int
    ): List<UsuarioDTO>

    @GET("/api/connections/outgoing/{userId}")
    suspend fun outgoingConnections(
        @Path("userId") userId: Long
    ): List<OutgoingConnectionDTO>
}
