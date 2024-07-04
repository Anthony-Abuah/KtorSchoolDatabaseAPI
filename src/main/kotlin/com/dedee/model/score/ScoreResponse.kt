package com.dedee.model.score

@kotlinx.serialization.Serializable
data class ScoreResponse(
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
){
    fun toScoreInfo(): ScoreInfo{
        return ScoreInfo(uniqueScoreId, scoreType, marks, grade, remarks, classAverage)
    }
}
