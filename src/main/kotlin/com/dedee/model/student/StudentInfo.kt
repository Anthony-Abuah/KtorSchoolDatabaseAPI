package com.dedee.model.student

@kotlinx.serialization.Serializable
data class StudentInfo(
    val firstName: String?,
    val lastName: String?,
    val otherNames: String?,
    val uniqueStudentId: String,
    val uniqueClassId: String?,
    val gender: String,
    val photo: String?,
    val teachers: String?,
    val gradings: String?,
    val conducts: String?,
    val isEnrolled: Boolean?,
)
