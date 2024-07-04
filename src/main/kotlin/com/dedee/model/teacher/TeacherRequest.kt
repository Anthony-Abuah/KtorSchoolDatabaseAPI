package com.dedee.model.teacher

@kotlinx.serialization.Serializable
data class TeacherRequest(
    val firstName: String?,
    val lastName: String?,
    val otherNames: String?,
    val teacherUsername: String?,
    val password: String,
    val email: String,
    val contact: String,
    val gender: String,
    val subjects: String,
    val classes: String,
)
