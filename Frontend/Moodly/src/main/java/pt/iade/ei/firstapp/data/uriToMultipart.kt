package pt.iade.ei.firstapp.data

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun uriToMultipart(context: Context, uri: Uri): MultipartBody.Part {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
        ?: throw IllegalStateException("Não foi possível abrir a imagem")

    val bytes = inputStream.readBytes()
    inputStream.close()

    val requestFile = bytes.toRequestBody(
        "image/*".toMediaTypeOrNull(),
        0,
        bytes.size
    )

    val fileName = "profile-${System.currentTimeMillis()}.jpg"

    return MultipartBody.Part.createFormData(
        "file",
        fileName,
        requestFile
    )
}
