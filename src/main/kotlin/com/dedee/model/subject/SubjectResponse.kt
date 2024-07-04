package com.dedee.model.subject

@kotlinx.serialization.Serializable
data class SubjectResponse(
    val subjectName: String?,
    val uniqueSubjectId: String?,
    val classes: String?,
    val teachers: String?
){
    fun toSubjectInfo(): SubjectInfo{
        return SubjectInfo(subjectName, uniqueSubjectId, teachers)
    }
}
