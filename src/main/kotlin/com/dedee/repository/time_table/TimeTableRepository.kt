package com.dedee.repository.time_table

import com.dedee.model.time_table.TimeTableRequest
import com.dedee.model.time_table.TimeTableResponse
import util.ResponseFeedback


interface TimeTableRepository {
    suspend fun getAllTimeTables(): ResponseFeedback<List<TimeTableResponse>?>

    suspend fun addTimeTable(timeTable: TimeTableRequest): ResponseFeedback<TimeTableResponse?>

    suspend fun getTimeTable(uniqueTimeTableId: String): ResponseFeedback<List<TimeTableResponse>?>

    suspend fun deleteTimeTable(uniqueTimeTableId: String): ResponseFeedback<String>

    suspend fun updateTimeTable(uniqueTimeTableId: String, updatedTimeTable: TimeTableRequest): ResponseFeedback<TimeTableResponse?>

    
}



