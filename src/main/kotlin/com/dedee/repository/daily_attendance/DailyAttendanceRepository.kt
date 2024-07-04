package com.dedee.repository.daily_attendance

import com.dedee.model.daily_attendance.DailyAttendanceRequest
import com.dedee.model.daily_attendance.DailyAttendanceResponse
import util.ResponseFeedback


interface DailyAttendanceRepository {
    suspend fun getAllDailyAttendances(): ResponseFeedback<List<DailyAttendanceResponse>?>

    suspend fun addDailyAttendance(dailyAttendance: DailyAttendanceRequest): ResponseFeedback<DailyAttendanceResponse?>

    suspend fun getDailyAttendance(uniqueDailyAttendanceId: String): ResponseFeedback<DailyAttendanceResponse?>

    suspend fun deleteDailyAttendance(uniqueDailyAttendanceId: String): ResponseFeedback<String>

    suspend fun updateDailyAttendance(uniqueDailyAttendanceId: String, updatedDailyAttendance: DailyAttendanceRequest): ResponseFeedback<DailyAttendanceResponse?>

    
}



