package com.dedee.model.helper_tables

@kotlinx.serialization.Serializable
data class StudentSubject(
    val uniqueStudentId: String,
    val uniqueSubjectId: String
)
