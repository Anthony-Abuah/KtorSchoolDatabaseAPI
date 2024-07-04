package com.dedee.model.subject

@kotlinx.serialization.Serializable
data class SubjectInfo(
    val subjectName: String?,
    val uniqueSubjectId: String?,
    val teachers: String?
)
