package pt.iade.ei.firstapp.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ConnectionApi {

    data class ConnectionSuggestionDTO(
        @SerializedName("userId") val userId: Long,
        @SerializedName("nome") val nome: String,
        @SerializedName("fotoPerfil") val fotoPerfil: String?,
        @SerializedName("musicInterests") val musicInterests: List<String>?,
        @SerializedName("moviesInterests") val moviesInterests: List<String>?,
        @SerializedName("gamesInterests") val gamesInterests: List<String>?
    )

    data class ConnectionDTO(
        @SerializedName("connectionId") val connectionId: Long,
        @SerializedName("userId") val userId: Long,
        @SerializedName("nome") val nome: String,
        @SerializedName("fotoPerfil") val fotoPerfil: String?
    )

    data class ConnectionRequestBody(
        @SerializedName("remetenteId") val remetenteId: Long,
        @SerializedName("destinatarioId") val destinatarioId: Long
    )

    data class ConnectionResponseBody(
        @SerializedName("estado") val estado: String
    )

    @GET("/api/connections/discover/{userId}")
    suspend fun discoverConnections(
        @Path("userId") userId: Long,
        @Query("limit") limit: Int = 1
    ): List<ConnectionSuggestionDTO>

    @POST("/api/connections/request")
    suspend fun requestConnection(
        @Body body: ConnectionRequestBody
    ): Response<Void>

    @POST("/api/connections/{connectionId}/respond")
    suspend fun respondConnection(
        @Path("connectionId") connectionId: Long,
        @Body body: ConnectionResponseBody
    ): Response<Void>

    @GET("/api/connections/mutual/{userId}")
    suspend fun mutualConnections(
        @Path("userId") userId: Long
    ): List<ConnectionDTO>

    @GET("/api/connections/pending/{userId}")
    suspend fun pendingConnections(
        @Path("userId") userId: Long
    ): List<ConnectionDTO>
}
