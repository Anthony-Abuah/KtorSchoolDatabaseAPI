package com.dedee.repository.time_table

import com.dedee.model.time_table.TimeTableRequest
import com.dedee.model.time_table.TimeTableResponse
import com.dedee.service.time_table.TimeTableService
import util.Constants.emptyString
import util.Functions.uniqueTimeTableIdGenerator
import util.ResponseFeedback

class TimeTableRepositoryImpl(
    private val timeTableService: TimeTableService,
) : TimeTableRepository{
    override suspend fun getAllTimeTables(): ResponseFeedback<List<TimeTableResponse>?> {
        val timeTableResponse = timeTableService.getAllTimeTables()
        return if (timeTableResponse.isNullOrEmpty()) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch all timeTables",
                success = false
            )
        }else{
            ResponseFeedback(
                data = timeTableResponse,
                message = "All timeTables successfully loaded",
                success = true
            )
        }
    }

    override suspend fun addTimeTable(timeTable: TimeTableRequest): ResponseFeedback<TimeTableResponse?> {
        val timeTableType = timeTable.timeTableType ?: emptyString
        val uniqueClassId = timeTable.uniqueClassId ?: emptyString
        val uniqueSubjectId = timeTable.uniqueSubjectId ?: emptyString
        val uniqueTimeTableName = uniqueTimeTableIdGenerator(timeTableType, uniqueSubjectId, uniqueClassId)
        return if (uniqueTimeTableIdExists(uniqueTimeTableName)){
            ResponseFeedback(
                data = null,
                message = "Cannot add time table because timeTable already exists",
                success = false
            )
        }else{
            val registerResponse = timeTableService.addTimeTable(timeTable, uniqueTimeTableName)
            if (registerResponse < 1 ) {
                ResponseFeedback(
                    data = null,
                    message = "Unable to add timeTable",
                    success = false
                )
            }else{
                val registeredTimeTable = timeTableService.getTimeTable(uniqueTimeTableName)?.first()
                ResponseFeedback(
                    data = registeredTimeTable,
                    message = "TimeTable successfully added",
                    success = true
                )
            }
        }
    }

    override suspend fun getTimeTable(
        uniqueTimeTableId: String,
    ): ResponseFeedback<List<TimeTableResponse>?> {
        val response = timeTableService.getTimeTable(uniqueTimeTableId)
        return if (response.isNullOrEmpty()){
            ResponseFeedback(
                data = null,
                message = "No timeTable matches the given criteria",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "TimeTables fetched successfully",
                success = true
            )
        }
    }

    override suspend fun deleteTimeTable(uniqueTimeTableId: String): ResponseFeedback<String> {
        val response =  timeTableService.deleteTimeTable(uniqueTimeTableId) ?: -1
        return if (response < 1){
            ResponseFeedback(
                data = "Unable to delete timeTable",
                message = "Unknown Error",
                success = false
            )
        }else{
            ResponseFeedback(
                data = "TimeTable deleted successfully",
                message = "TimeTable deleted successfully",
                success = true
            )
        }

    }

    override suspend fun updateTimeTable(
        uniqueTimeTableId: String,
        updatedTimeTable: TimeTableRequest,
    ): ResponseFeedback<TimeTableResponse?> {
        val response = timeTableService.updateTimeTable(uniqueTimeTableId, updatedTimeTable)
        return if (response == null){
            ResponseFeedback(
                data = null,
                message = "Unable to update timeTable",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "TimeTable updated successfully",
                success = true
            )
        }
    }


    private suspend fun uniqueTimeTableIdExists(uniqueTimeTableId: String): Boolean {
        return !(timeTableService.getTimeTable(uniqueTimeTableId).isNullOrEmpty())
    }

}