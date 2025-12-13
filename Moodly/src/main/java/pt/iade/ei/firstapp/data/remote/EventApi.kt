package pt.iade.ei.firstapp.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApi {

    data class CreateEventBody(
        @SerializedName("criadorId") val criadorId: Long,
        @SerializedName("titulo") val titulo: String,
        @SerializedName("descricao") val descricao: String?,
        @SerializedName("local") val local: String?,
        @SerializedName("dataEvento") val dataEvento: String?,
        @SerializedName("convidadosIds") val convidadosIds: List<Long>
    )

    data class EventDTO(
        @SerializedName("id") val id: Long,
        @SerializedName("titulo") val titulo: String,
        @SerializedName("descricao") val descricao: String?,
        @SerializedName("local") val local: String?,
        @SerializedName("dataEvento") val dataEvento: String?,
        @SerializedName("estado") val estado: String?,
        @SerializedName("isOwner") val isOwner: Boolean,
        @SerializedName("criadorNome") val criadorNome: String?
    )

    @POST("/api/events")
    suspend fun createEvent(@Body body: CreateEventBody): Response<Void>

    @GET("/api/events/user/{userId}")
    suspend fun eventsForUser(@Path("userId") userId: Long): List<EventDTO>

    @POST("/api/events/{eventId}/accept/{userId}")
    suspend fun acceptInvite(@Path("eventId") eventId: Long, @Path("userId") userId: Long): Response<Void>

    @POST("/api/events/{eventId}/leave/{userId}")
    suspend fun leaveEvent(@Path("eventId") eventId: Long, @Path("userId") userId: Long): Response<Void>

    @POST("/api/events/{eventId}/cancel/{userId}")
    suspend fun cancelEvent(@Path("eventId") eventId: Long, @Path("userId") userId: Long): Response<Void>

    @DELETE("/api/events/{eventId}/hide/{userId}")
    suspend fun hideEvent(@Path("eventId") eventId: Long, @Path("userId") userId: Long): Response<Void>
}
