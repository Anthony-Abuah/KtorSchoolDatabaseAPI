package com.dedee.model.score

@kotlinx.serialization.Serializable
data class ScoreInfo(
    val uniqueScoreId: String?,
    val scoreType: String?,
    val marks: Double?,
    val grade: String?,
    val remarks: String?,
    val classAverage: Double?,
)
