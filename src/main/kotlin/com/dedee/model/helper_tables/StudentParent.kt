package com.dedee.model.helper_tables

@kotlinx.serialization.Serializable
data class StudentParent(
    val uniqueStudentId: String,
    val parentUsername: String
)
