package com.dedee.model.grading

@kotlinx.serialization.Serializable
data class GradingResponse(
    val uniqueGradingId: String?,
    val uniqueStudentId: String?,
    val uniqueSubjectId: String?,
    val uniqueClassId: String?,
    val uniqueTeacherId: String?,
    val uniqueTermName: String?,
    val finalGrade: String?,
    val teacherRemarks: String?,
    val gradeRemarks: String?,
    val scores: String?,
){
    fun toGradingInfo(): GradingInfo{
        return GradingInfo(uniqueGradingId, uniqueTermName, finalGrade, teacherRemarks, gradeRemarks, scores)
    }
}

