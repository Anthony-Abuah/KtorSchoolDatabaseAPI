package com.dedee.model.grading

@kotlinx.serialization.Serializable
data class GradingRequest(
    val uniqueGradingId: String?,
    val uniqueStudentId: String?,
    val uniqueSubjectId: String?,
    val uniqueTeacherId: String?,
    val uniqueClassId: String?,
    val uniqueTermName: String?,
    val finalGrade: String?,
    val teacherRemarks: String?,
    val gradeRemarks: String?,
    val scores: String?,
)
