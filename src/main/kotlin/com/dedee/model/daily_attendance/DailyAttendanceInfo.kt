package com.dedee.model.daily_attendance

@kotlinx.serialization.Serializable
data class DailyAttendanceInfo(
    val uniqueDailyAttendanceId: String?,
    val date: Int?,
    val isPresent: Boolean?
)
