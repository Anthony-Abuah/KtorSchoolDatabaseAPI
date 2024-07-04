package com.dedee.model.score

@kotlinx.serialization.Serializable
data class ScoreRequest(
    val uniqueScoreId: String?,
    val uniqueGradingId: String?,
    val uniqueStudentId: String?,
    val uniqueSubjectId: String?,
    val uniqueClassId: String?,
    val uniqueTermName: String?,
    val scoreType: String?,
    val marks: Double?,
    val grade: String?,
    val remarks: String?,
    val classAverage: Double?,
)
