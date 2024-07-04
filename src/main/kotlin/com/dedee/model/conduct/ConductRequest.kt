package com.dedee.model.conduct

@kotlinx.serialization.Serializable
data class ConductRequest(
    val uniqueConductId: String?,
    val uniqueStudentId: String?,
    val uniqueAttendanceId: String?,
    val uniqueTeacherId: String?,
    val uniqueClassId: String?,
    val uniqueTermName: String?,
    val conduct: String?,
    val interests: String?
)
