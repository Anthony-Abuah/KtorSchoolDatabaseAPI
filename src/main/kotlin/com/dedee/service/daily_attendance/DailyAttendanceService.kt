package com.dedee.service.daily_attendance

import com.dedee.model.daily_attendance.DailyAttendanceRequest
import com.dedee.model.daily_attendance.DailyAttendanceResponse

interface DailyAttendanceService {

    suspend fun getAllDailyAttendances(): List<DailyAttendanceResponse>?

    suspend fun addDailyAttendance(dailyAttendance: DailyAttendanceRequest, uniqueDailyAttendanceId: String): Int

    suspend fun getDailyAttendance(uniqueDailyAttendanceId: String): DailyAttendanceResponse?

    suspend fun updateDailyAttendance(uniqueDailyAttendanceId: String, updatedDailyAttendance: DailyAttendanceRequest): DailyAttendanceResponse?

    suspend fun deleteDailyAttendance(uniqueDailyAttendanceId: String): Int?

}