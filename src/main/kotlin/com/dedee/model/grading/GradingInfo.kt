package com.dedee.model.grading

@kotlinx.serialization.Serializable
data class GradingInfo(
    val uniqueGradingId: String?,
    val uniqueTermName: String?,
    val finalGrade: String?,
    val teacherRemarks: String?,
    val gradeRemarks: String?,
    val scores: String?,
)
