package com.dedee.model.daily_attendance

@kotlinx.serialization.Serializable
data class DailyAttendanceRequest(
    val uniqueDailyAttendanceId: String?,
    val uniqueAttendanceId: String?,
    val uniqueStudentId: String?,
    val uniqueClassId: String?,
    val uniqueTermName: String?,
    val date: Int?,
    val isPresent: Boolean?
)
