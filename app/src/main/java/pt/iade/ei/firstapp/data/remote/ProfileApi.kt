package pt.iade.ei.firstapp.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileApi {

    data class InterestDTO(
        @SerializedName("tipo") val tipo: String,
        @SerializedName("nome") val nome: String
    )

    data class UserProfileUpdateRequest(
        @SerializedName("fotoPerfil") val fotoPerfil: String?,
        @SerializedName("interesses") val interesses: List<InterestDTO>
    )

    data class UserProfileResponse(
        @SerializedName("id") val id: Long,
        @SerializedName("nome") val nome: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("fotoPerfil") val fotoPerfil: String?,
        @SerializedName("interesses") val interesses: List<InterestDTO>?
    )

    @GET("/api/profile/{userId}")
    suspend fun getProfile(
        @Path("userId") userId: Long
    ): Response<UserProfileResponse>

    @PUT("/api/profile/{userId}")
    suspend fun updateProfile(
        @Path("userId") userId: Long,
        @Body req: UserProfileUpdateRequest
    ): Response<Unit>
}
