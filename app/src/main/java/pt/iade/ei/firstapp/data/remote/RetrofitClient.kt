package pt.iade.ei.firstapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit

/**
 * Single Retrofit instance for the app.
 * - Use http://10.0.2.2:8080/ for Android emulator -> host machine.
 * - Logs HTTP bodies in debug builds.
 * - Exposes BOTH: `instance` (Retrofit) and typed helpers (authApi(), profileApi()).
 */
object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    /** For older code that expects `RetrofitClient.instance.create(Api::class.java)` */
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /** Prefer these type-safe helpers in new code */
    fun authApi(): AuthApi = instance.create(AuthApi::class.java)
    fun profileApi(): ProfileApi = instance.create(ProfileApi::class.java)
}
