package com.dedee.repository.attendance

import com.dedee.model.attendance.AttendanceRequest
import com.dedee.model.attendance.AttendanceResponse
import com.dedee.service.attendance.AttendanceService
import util.Constants.emptyString
import util.Functions.uniqueAttendanceIdGenerator
import util.ResponseFeedback

class AttendanceRepositoryImpl(
    private val attendanceService: AttendanceService,
) : AttendanceRepository{
    override suspend fun getAllAttendances(): ResponseFeedback<List<AttendanceResponse>?> {
        val attendanceResponse = attendanceService.getAllAttendances()
        return if (attendanceResponse.isNullOrEmpty()) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch all attendances",
                success = false
            )
        }else{
            ResponseFeedback(
                data = attendanceResponse,
                message = "All attendances successfully loaded",
                success = true
            )
        }
    }

    override suspend fun addAttendance(attendance: AttendanceRequest): ResponseFeedback<AttendanceResponse?> {
        val uniqueStudentId = attendance.uniqueStudentId ?: emptyString
        val uniqueTermName = attendance.uniqueTermName ?: emptyString
        val uniqueAttendanceId = uniqueAttendanceIdGenerator(uniqueStudentId, uniqueTermName)
        return if (uniqueAttendanceIdExists(uniqueAttendanceId)){
            ResponseFeedback(
                data = null,
                message = "Cannot add attendance because it already exists",
                success = false
            )
        }else{
            val registerResponse = attendanceService.addAttendance(attendance, uniqueAttendanceId)
            if (registerResponse < 1 ) {
                ResponseFeedback(
                    data = null,
                    message = "Unable to register attendance",
                    success = false
                )
            }else{
                val registeredAttendance = attendanceService.getAttendance(uniqueAttendanceId)
                ResponseFeedback(
                    data = registeredAttendance,
                    message = "Attendance successfully registered",
                    success = true
                )
            }
        }
    }

    override suspend fun getAttendance(
        uniqueAttendanceId: String,
    ): ResponseFeedback<AttendanceResponse?> {
        val response = attendanceService.getAttendance(uniqueAttendanceId)
        return if (response == null){
            ResponseFeedback(
                data = null,
                message = "No attendance matches the given criteria",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Attendances fetched successfully",
                success = true
            )
        }
    }

    override suspend fun deleteAttendance(uniqueAttendanceId: String): ResponseFeedback<String> {
        val response =  attendanceService.deleteAttendance(uniqueAttendanceId) ?: -1
        return if (response < 1){
            ResponseFeedback(
                data = "Unable to delete attendance",
                message = "Unknown Error",
                success = false
            )
        }else{
            ResponseFeedback(
                data = "Attendance deleted successfully",
                message = "Attendance deleted successfully",
                success = true
            )
        }

    }

    override suspend fun updateAttendance(
        uniqueAttendanceId: String,
        updatedAttendance: AttendanceRequest,
    ): ResponseFeedback<AttendanceResponse?> {
        val response = attendanceService.updateAttendance(uniqueAttendanceId, updatedAttendance)
        return if (response == null){
            ResponseFeedback(
                data = null,
                message = "Unable to update attendance",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Attendance updated successfully",
                success = true
            )
        }
    }


    private suspend fun uniqueAttendanceIdExists(uniqueAttendanceId: String): Boolean {
        return attendanceService.getAttendance(uniqueAttendanceId) != null
    }

}