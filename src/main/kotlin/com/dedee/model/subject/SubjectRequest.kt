package com.dedee.model.subject

@kotlinx.serialization.Serializable
data class SubjectRequest(
    val subjectName: String?,
    val uniqueSubjectId: String?,
    val classes: String?,
    val teachers: String?
)
