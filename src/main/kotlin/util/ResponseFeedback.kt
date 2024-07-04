package util


@kotlinx.serialization.Serializable
data class ResponseFeedback<T>(
    val data: T? = null,
    val message: String? = null,
    val success: Boolean = false
)
