package com.dedee.repository.attendance

import com.dedee.model.attendance.AttendanceRequest
import com.dedee.model.attendance.AttendanceResponse
import util.ResponseFeedback


interface AttendanceRepository {
    suspend fun getAllAttendances(): ResponseFeedback<List<AttendanceResponse>?>

    suspend fun addAttendance(attendance: AttendanceRequest): ResponseFeedback<AttendanceResponse?>

    suspend fun getAttendance(uniqueAttendanceId: String): ResponseFeedback<AttendanceResponse?>

    suspend fun deleteAttendance(uniqueAttendanceId: String): ResponseFeedback<String>

    suspend fun updateAttendance(uniqueAttendanceId: String, updatedAttendance: AttendanceRequest): ResponseFeedback<AttendanceResponse?>

    
}



