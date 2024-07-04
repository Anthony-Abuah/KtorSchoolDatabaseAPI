package com.dedee.model.attendance

@kotlinx.serialization.Serializable
data class AttendanceRequest(
    val uniqueAttendanceId: String?,
    val uniqueStudentId: String?,
    val uniqueClassId: String?,
    val uniqueTermName: String?,
    val dailyAttendances: String?,
    val absentDates: String?,
    val numberOfAttendedDays: Int?,
    val numberOfAbsentDays: Int?,
    val numberOfSchoolDays: Int?,
)
