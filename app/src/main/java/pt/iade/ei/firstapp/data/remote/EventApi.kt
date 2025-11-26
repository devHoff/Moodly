package pt.iade.ei.firstapp.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
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
        @SerializedName("dataEvento") val dataEvento: String?
    )

    data class InviteDTO(
        @SerializedName("inviteId") val inviteId: Long,
        @SerializedName("eventoId") val eventoId: Long,
        @SerializedName("titulo") val titulo: String,
        @SerializedName("estado") val estado: String?
    )

    data class InviteResponseBody(
        @SerializedName("estado") val estado: String
    )

    @POST("/api/events")
    suspend fun createEvent(
        @Body body: CreateEventBody
    ): Response<Void>

    @GET("/api/events/user/{userId}")
    suspend fun eventsForUser(
        @Path("userId") userId: Long
    ): List<EventDTO>

    @GET("/api/events/invites/{userId}")
    suspend fun invitesForUser(
        @Path("userId") userId: Long
    ): List<InviteDTO>

    @POST("/api/events/{eventId}/respond")
    suspend fun respondInvite(
        @Path("eventId") eventId: Long,
        @Body body: InviteResponseBody
    ): Response<Void>
}

