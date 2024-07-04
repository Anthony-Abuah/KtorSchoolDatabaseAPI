package com.dedee.service.attendance

import com.dedee.model.attendance.AttendanceRequest
import com.dedee.model.attendance.AttendanceResponse

interface AttendanceService {

    suspend fun getAllAttendances(): List<AttendanceResponse>?

    suspend fun addDailyAttendance(dailyAttendance: String, uniqueAttendanceId: String)

    suspend fun addAttendance(attendance: AttendanceRequest, uniqueAttendanceId: String): Int

    suspend fun getAttendance(uniqueAttendanceId: String): AttendanceResponse?

    suspend fun updateAttendance(uniqueAttendanceId: String, updatedAttendance: AttendanceRequest): AttendanceResponse?

    suspend fun deleteAttendance(uniqueAttendanceId: String): Int?

}