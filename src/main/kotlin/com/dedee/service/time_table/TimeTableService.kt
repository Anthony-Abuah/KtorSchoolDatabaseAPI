package com.dedee.service.time_table

import com.dedee.model.time_table.TimeTableRequest
import com.dedee.model.time_table.TimeTableResponse

interface TimeTableService {

    suspend fun getAllTimeTables(): List<TimeTableResponse>?

    suspend fun addTimeTable(timeTable: TimeTableRequest, uniqueTimeTableId: String): Int

    suspend fun getTimeTable(uniqueTimeTableId: String): List<TimeTableResponse>?

    suspend fun updateTimeTable(uniqueTimeTableId: String, updatedTimeTable: TimeTableRequest): TimeTableResponse?

    suspend fun deleteTimeTable(uniqueTimeTableId: String): Int?

}