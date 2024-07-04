package com.dedee.service.daily_attendance

import com.dedee.entities.DailyAttendanceEntity
import com.dedee.model.daily_attendance.DailyAttendanceRequest
import com.dedee.model.daily_attendance.DailyAttendanceResponse
import com.dedee.service.attendance.AttendanceService
import com.google.gson.Gson
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString

class DailyAttendanceServiceImpl(
    private val db: Database,
) : DailyAttendanceService {
    override suspend fun getAllDailyAttendances(): List<DailyAttendanceResponse>? {
        val dailyAttendance = db.from(DailyAttendanceEntity).select()
            .map {
                val uniqueDailyAttendanceId = it[DailyAttendanceEntity.uniqueDailyAttendanceId] ?: emptyString
                val uniqueAttendanceId = it[DailyAttendanceEntity.uniqueAttendanceId] ?: emptyString
                val uniqueClassId = it[DailyAttendanceEntity.uniqueDailyAttendanceId] ?: emptyString
                val uniqueStudentId = it[DailyAttendanceEntity.uniqueDailyAttendanceId] ?: emptyString
                val uniqueTermName = it[DailyAttendanceEntity.uniqueDailyAttendanceId] ?: emptyString
                val isPresent = it[DailyAttendanceEntity.isPresent] ?: false
                val date = it[DailyAttendanceEntity.date] ?: 0
                DailyAttendanceResponse(uniqueDailyAttendanceId, uniqueAttendanceId, uniqueStudentId, uniqueClassId, uniqueTermName, date, isPresent)
            }
        return dailyAttendance.ifEmpty { null }
    }

    override suspend fun addDailyAttendance(dailyAttendance: DailyAttendanceRequest, uniqueDailyAttendanceId: String): Int {
        return db.insert(DailyAttendanceEntity){
            set(it.uniqueDailyAttendanceId, dailyAttendance.uniqueDailyAttendanceId)
            set(it.uniqueAttendanceId, dailyAttendance.uniqueAttendanceId)
            set(it.uniqueClassId, dailyAttendance.uniqueClassId)
            set(it.uniqueStudentId, dailyAttendance.uniqueStudentId)
            set(it.uniqueTermName, dailyAttendance.uniqueTermName)
            set(it.isPresent, dailyAttendance.isPresent)
            set(it.date, dailyAttendance.date)
        }
    }

    override suspend fun getDailyAttendance(uniqueDailyAttendanceId: String): DailyAttendanceResponse? {
        return db.from(DailyAttendanceEntity)
            .select()
            .where{ DailyAttendanceEntity.uniqueDailyAttendanceId eq uniqueDailyAttendanceId}
            .map {
                val uniqueAttendanceId = it[DailyAttendanceEntity.uniqueAttendanceId] ?: emptyString
                val uniqueClassId = it[DailyAttendanceEntity.uniqueDailyAttendanceId] ?: emptyString
                val uniqueStudentId = it[DailyAttendanceEntity.uniqueDailyAttendanceId] ?: emptyString
                val uniqueTermName = it[DailyAttendanceEntity.uniqueDailyAttendanceId] ?: emptyString
                val isPresent = it[DailyAttendanceEntity.isPresent] ?: false
                val date = it[DailyAttendanceEntity.date] ?: 0
                DailyAttendanceResponse(uniqueDailyAttendanceId, uniqueAttendanceId, uniqueStudentId, uniqueClassId, uniqueTermName, date, isPresent)
            }.firstOrNull()
    }

    override suspend fun updateDailyAttendance(uniqueDailyAttendanceId: String, updatedDailyAttendance: DailyAttendanceRequest): DailyAttendanceResponse? {
        val rowsAffected = db.update(DailyAttendanceEntity){
            set(it.uniqueAttendanceId, updatedDailyAttendance.uniqueAttendanceId)
            set(it.uniqueClassId, updatedDailyAttendance.uniqueClassId)
            set(it.uniqueStudentId, updatedDailyAttendance.uniqueStudentId)
            set(it.uniqueTermName, updatedDailyAttendance.uniqueTermName)
            set(it.isPresent, updatedDailyAttendance.isPresent)
            set(it.date, updatedDailyAttendance.date)
            where { it.uniqueDailyAttendanceId eq uniqueDailyAttendanceId }
        }
        return if (rowsAffected < 1){
            null
        }else {
            db.from(DailyAttendanceEntity)
                .select()
                .where { DailyAttendanceEntity.uniqueDailyAttendanceId eq uniqueDailyAttendanceId }
                .map {
                    val uniqueAttendanceId = it[DailyAttendanceEntity.uniqueAttendanceId] ?: emptyString
                    val uniqueClassId = it[DailyAttendanceEntity.uniqueDailyAttendanceId] ?: emptyString
                    val uniqueStudentId = it[DailyAttendanceEntity.uniqueDailyAttendanceId] ?: emptyString
                    val uniqueTermName = it[DailyAttendanceEntity.uniqueDailyAttendanceId] ?: emptyString
                    val isPresent = it[DailyAttendanceEntity.isPresent] ?: false
                    val date = it[DailyAttendanceEntity.date] ?: 0
                    DailyAttendanceResponse(uniqueDailyAttendanceId, uniqueAttendanceId, uniqueStudentId, uniqueClassId, uniqueTermName, date, isPresent)
                }.firstOrNull()
        }
    }

    override suspend fun deleteDailyAttendance(uniqueDailyAttendanceId: String): Int {
        return db.delete(DailyAttendanceEntity) {
            DailyAttendanceEntity.uniqueDailyAttendanceId eq uniqueDailyAttendanceId
        }
    }

}