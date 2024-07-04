package com.dedee.service.time_table

import com.dedee.entities.TimeTableEntity
import com.dedee.model.time_table.TimeTableRequest
import com.dedee.model.time_table.TimeTableResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString

class TimeTableServiceImpl(
    private val db: Database,
) : TimeTableService{
    override suspend fun getAllTimeTables(): List<TimeTableResponse>? {
        val timeTable = db.from(TimeTableEntity).select()
            .map {
                val uniqueTimeTableId = it[TimeTableEntity.uniqueTimeTableId] ?: emptyString
                val uniqueSubjectId = it[TimeTableEntity.uniqueSubjectId] ?: emptyString
                val uniqueClassId = it[TimeTableEntity.uniqueClassId] ?: emptyString
                val uniqueTeacherId = it[TimeTableEntity.uniqueTeacherId] ?: emptyString
                val uniqueTermName = it[TimeTableEntity.uniqueTermName] ?: emptyString
                val timeTableType = it[TimeTableEntity.timeTableType] ?: emptyString
                val days = it[TimeTableEntity.days] ?: emptyString
                val startTime = it[TimeTableEntity.startTime] ?: emptyString
                val endTime = it[TimeTableEntity.endTime] ?: emptyString
                TimeTableResponse(uniqueTimeTableId, uniqueSubjectId, uniqueClassId, uniqueTeacherId, uniqueTermName, timeTableType, days, startTime, endTime)
            }
        return timeTable.ifEmpty { null }
    }

    override suspend fun addTimeTable(timeTable: TimeTableRequest, uniqueTimeTableId: String): Int {
        return db.insert(TimeTableEntity){
            set(it.uniqueTimeTableId, uniqueTimeTableId)
            set(it.uniqueTermName, timeTable.uniqueTermName)
            set(it.uniqueClassId, timeTable.uniqueClassId)
            set(it.uniqueSubjectId, timeTable.uniqueSubjectId)
            set(it.uniqueTeacherId, timeTable.uniqueTeacherId)
            set(it.days, timeTable.uniqueTermName)
            set(it.timeTableType, timeTable.timeTableType)
            set(it.startTime, timeTable.startTime)
            set(it.endTime, timeTable.endTime)
        }
    }

    override suspend fun getTimeTable(uniqueTimeTableId: String): List<TimeTableResponse> {
        return db.from(TimeTableEntity)
            .select()
            .where { TimeTableEntity.uniqueTimeTableId eq uniqueTimeTableId }
            .map {
                val uniqueSubjectId = it[TimeTableEntity.uniqueSubjectId] ?: emptyString
                val uniqueClassId = it[TimeTableEntity.uniqueClassId] ?: emptyString
                val uniqueTeacherId = it[TimeTableEntity.uniqueTeacherId] ?: emptyString
                val uniqueTermName = it[TimeTableEntity.uniqueTermName] ?: emptyString
                val timeTableType = it[TimeTableEntity.timeTableType] ?: emptyString
                val days = it[TimeTableEntity.days] ?: emptyString
                val startTime = it[TimeTableEntity.startTime] ?: emptyString
                val endTime = it[TimeTableEntity.endTime] ?: emptyString
                TimeTableResponse(uniqueTimeTableId, uniqueSubjectId, uniqueClassId, uniqueTeacherId, uniqueTermName, timeTableType, days, startTime, endTime)
            }


    }

    override suspend fun updateTimeTable(uniqueTimeTableId: String, updatedTimeTable: TimeTableRequest): TimeTableResponse? {
        val rowsAffected = db.update(TimeTableEntity){
            set(it.uniqueTermName, updatedTimeTable.uniqueTermName)
            set(it.uniqueClassId, updatedTimeTable.uniqueClassId)
            set(it.uniqueSubjectId, updatedTimeTable.uniqueSubjectId)
            set(it.uniqueTeacherId, updatedTimeTable.uniqueTeacherId)
            set(it.days, updatedTimeTable.uniqueTermName)
            set(it.timeTableType, updatedTimeTable.timeTableType)
            set(it.startTime, updatedTimeTable.startTime)
            set(it.endTime, updatedTimeTable.endTime)
            where { it.uniqueTimeTableId eq uniqueTimeTableId }
        }
        return if (rowsAffected < 1){
            null
        }else {
            db.from(TimeTableEntity)
                .select()
                .where { TimeTableEntity.uniqueTimeTableId eq uniqueTimeTableId }
                .map {
                    val uniqueSubjectId = it[TimeTableEntity.uniqueSubjectId] ?: emptyString
                    val uniqueClassId = it[TimeTableEntity.uniqueClassId] ?: emptyString
                    val uniqueTeacherId = it[TimeTableEntity.uniqueTeacherId] ?: emptyString
                    val uniqueTermName = it[TimeTableEntity.uniqueTermName] ?: emptyString
                    val timeTableType = it[TimeTableEntity.timeTableType] ?: emptyString
                    val days = it[TimeTableEntity.days] ?: emptyString
                    val startTime = it[TimeTableEntity.startTime] ?: emptyString
                    val endTime = it[TimeTableEntity.endTime] ?: emptyString
                    TimeTableResponse(uniqueTimeTableId, uniqueSubjectId, uniqueClassId, uniqueTeacherId, uniqueTermName, timeTableType, days, startTime, endTime)
                }.firstOrNull()
        }
    }

    override suspend fun deleteTimeTable(uniqueTimeTableId: String): Int {
        return db.delete(TimeTableEntity) {
            TimeTableEntity.uniqueTimeTableId eq uniqueTimeTableId
        }
    }

}