package com.dedee.model.helper_tables

@kotlinx.serialization.Serializable
data class TeacherSubject(
    val uniqueTeacherId: String,
    val uniqueSubjectId: String
)
