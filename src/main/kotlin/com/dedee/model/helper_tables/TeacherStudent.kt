package com.dedee.model.helper_tables

@kotlinx.serialization.Serializable
data class TeacherStudent(
    val uniqueTeacherId: String,
    val uniqueStudentId: String
)
