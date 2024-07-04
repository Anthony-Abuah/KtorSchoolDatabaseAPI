package com.dedee.model.student

@kotlinx.serialization.Serializable
data class StudentRequest(
    val firstName: String,
    val lastName: String,
    val otherNames: String?,
    val uniqueStudentId: String,
    val uniqueClassId: String,
    val dateOfBirth: Int?,
    val email: String?,
    val gender: String,
    val photo: String?,
    val parents: String?,
    val teachers: String?,
    val subjects: String?,
    val classes: String?,
    val gradings: String?,
    val conducts: String?
)
