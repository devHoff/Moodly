package pt.iade.ei.firstapp.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path
import com.google.gson.annotations.SerializedName

interface ProfileApi {

    data class InterestDTO(
        @SerializedName("tipo") val tipo: String,
        @SerializedName("nome") val nome: String
    )

    data class UserProfileUpdateRequest(
        @SerializedName("fotoPerfil") val fotoPerfil: String?,
        @SerializedName("interesses") val interesses: List<InterestDTO>
    )

    // IMPORTANT: we don't try to parse the response body — just check 2xx
    @PUT("/api/profile/{userId}")
    suspend fun updateProfile(
        @Path("userId") userId: Long,
        @Body req: UserProfileUpdateRequest
    ): Response<Unit>
}
