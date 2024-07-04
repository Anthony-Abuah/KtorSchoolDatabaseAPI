package com.dedee.repository.daily_attendance

import com.dedee.model.daily_attendance.DailyAttendanceInfo
import com.dedee.model.daily_attendance.DailyAttendanceRequest
import com.dedee.model.daily_attendance.DailyAttendanceResponse
import com.dedee.service.attendance.AttendanceService
import com.dedee.service.daily_attendance.DailyAttendanceService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import util.Constants.emptyString
import util.Functions.uniqueDailyAttendanceIdGenerator
import util.ResponseFeedback

class DailyAttendanceRepositoryImpl(
    private val dailyAttendanceService: DailyAttendanceService,
    private val attendanceService: AttendanceService,
    private val gson: Gson,
) : DailyAttendanceRepository{
    override suspend fun getAllDailyAttendances(): ResponseFeedback<List<DailyAttendanceResponse>?> {
        val dailyAttendanceResponse = dailyAttendanceService.getAllDailyAttendances()
        return if (dailyAttendanceResponse.isNullOrEmpty()) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch all dailyAttendances",
                success = false
            )
        }else{
            ResponseFeedback(
                data = dailyAttendanceResponse,
                message = "All dailyAttendances successfully loaded",
                success = true
            )
        }
    }

    override suspend fun addDailyAttendance(dailyAttendance: DailyAttendanceRequest): ResponseFeedback<DailyAttendanceResponse?> {
        val uniqueStudentId = dailyAttendance.uniqueStudentId ?: emptyString
        val uniqueClassId = dailyAttendance.uniqueClassId ?: emptyString
        val uniqueTermName = dailyAttendance.uniqueTermName ?: emptyString
        val uniqueDailyAttendanceId = uniqueDailyAttendanceIdGenerator(uniqueStudentId, uniqueClassId, uniqueTermName)
        return if (uniqueDailyAttendanceIdExists(uniqueDailyAttendanceId)){
            ResponseFeedback(
                data = null,
                message = "Cannot add dailyAttendance because it already exists",
                success = false
            )
        }else{
            val registerResponse = dailyAttendanceService.addDailyAttendance(dailyAttendance, uniqueDailyAttendanceId)
            if (registerResponse < 1 ) {
                ResponseFeedback(
                    data = null,
                    message = "Unable to register dailyAttendance",
                    success = false
                )
            }else{
                val thisDailyAttendance = dailyAttendanceService.getDailyAttendance(uniqueDailyAttendanceId)
                val uniqueAttendanceId = dailyAttendance.uniqueAttendanceId
                val attendance = uniqueAttendanceId?.let { attendanceService.getAttendance(it) }
                val dailyAttendancesJSON = attendance?.dailyAttendances
                val listOfDailyAttendancesType = object : TypeToken<List<DailyAttendanceInfo>>() {}.type
                val dailyAttendancesList: List<DailyAttendanceInfo> = gson.fromJson(dailyAttendancesJSON, listOfDailyAttendancesType)
                val mutableListOfDailyAttendancesInfo = dailyAttendancesList.toMutableList()
                thisDailyAttendance?.toDailyAttendanceInfo()?.let { mutableListOfDailyAttendancesInfo.add(it) }
                val updatedDailyAttendancesListJSON = gson.toJson(mutableListOfDailyAttendancesInfo)
                attendanceService.addDailyAttendance(updatedDailyAttendancesListJSON, uniqueAttendanceId ?: emptyString)

                ResponseFeedback(
                    data = thisDailyAttendance,
                    message = "DailyAttendance successfully registered",
                    success = true
                )
            }
        }
    }

    override suspend fun getDailyAttendance(
        uniqueDailyAttendanceId: String,
    ): ResponseFeedback<DailyAttendanceResponse?> {
        val response = dailyAttendanceService.getDailyAttendance(uniqueDailyAttendanceId)
        return if (response == null){
            ResponseFeedback(
                data = null,
                message = "No dailyAttendance matches the given criteria",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "DailyAttendances fetched successfully",
                success = true
            )
        }
    }

    override suspend fun deleteDailyAttendance(uniqueDailyAttendanceId: String): ResponseFeedback<String> {
        val response =  dailyAttendanceService.deleteDailyAttendance(uniqueDailyAttendanceId) ?: -1
        return if (response < 1){
            ResponseFeedback(
                data = "Unable to delete dailyAttendance",
                message = "Unknown Error",
                success = false
            )
        }else{
            ResponseFeedback(
                data = "DailyAttendance deleted successfully",
                message = "DailyAttendance deleted successfully",
                success = true
            )
        }

    }

    override suspend fun updateDailyAttendance(
        uniqueDailyAttendanceId: String,
        updatedDailyAttendance: DailyAttendanceRequest,
    ): ResponseFeedback<DailyAttendanceResponse?> {
        val response = dailyAttendanceService.updateDailyAttendance(uniqueDailyAttendanceId, updatedDailyAttendance)
        return if (response == null){
            ResponseFeedback(
                data = null,
                message = "Unable to update dailyAttendance",
                success = false
            )
        }else{
            val thisDailyAttendance = dailyAttendanceService.getDailyAttendance(uniqueDailyAttendanceId)
            val uniqueAttendanceId = updatedDailyAttendance.uniqueAttendanceId
            val attendance = uniqueAttendanceId?.let { attendanceService.getAttendance(it) }
            val dailyAttendancesJSON = attendance?.dailyAttendances
            val listOfDailyAttendancesType = object : TypeToken<List<DailyAttendanceInfo>>() {}.type
            val dailyAttendancesList: List<DailyAttendanceInfo> = gson.fromJson(dailyAttendancesJSON, listOfDailyAttendancesType)
            val mutableListOfDailyAttendancesInfo = dailyAttendancesList.toMutableList()
            mutableListOfDailyAttendancesInfo.removeIf { it.uniqueDailyAttendanceId == uniqueDailyAttendanceId }
            thisDailyAttendance?.toDailyAttendanceInfo()?.let { mutableListOfDailyAttendancesInfo.add(it) }
            val updatedDailyAttendancesListJSON = gson.toJson(mutableListOfDailyAttendancesInfo)
            attendanceService.addDailyAttendance(updatedDailyAttendancesListJSON, uniqueAttendanceId ?: emptyString)

            ResponseFeedback(
                data = response,
                message = "DailyAttendance updated successfully",
                success = true
            )
        }
    }


    private suspend fun uniqueDailyAttendanceIdExists(uniqueDailyAttendanceId: String): Boolean {
        return dailyAttendanceService.getDailyAttendance(uniqueDailyAttendanceId) != null
    }

}