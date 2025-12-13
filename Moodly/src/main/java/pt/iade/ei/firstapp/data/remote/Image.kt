package pt.iade.ei.firstapp.data.remote


fun buildImageUrl(path: String?): String? {
    if (path.isNullOrBlank()) return null

    if (path.startsWith("http", ignoreCase = true) ||
        path.startsWith("content://", ignoreCase = true)
    ) {
        return path
    }

    return RetrofitClient.BASE_URL + path
}
