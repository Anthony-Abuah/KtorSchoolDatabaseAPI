package com.dedee.service.attendance

import com.dedee.entities.AttendanceEntity
import com.dedee.model.attendance.AttendanceRequest
import com.dedee.model.attendance.AttendanceResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString

class AttendanceServiceImpl(
    private val db: Database,
) : AttendanceService{
    override suspend fun getAllAttendances(): List<AttendanceResponse>? {
        val attendance = db.from(AttendanceEntity).select()
            .map {
                val uniqueAttendanceId = it[AttendanceEntity.uniqueAttendanceId] ?: emptyString
                val uniqueClassId = it[AttendanceEntity.uniqueAttendanceId] ?: emptyString
                val uniqueStudentId = it[AttendanceEntity.uniqueAttendanceId] ?: emptyString
                val uniqueTermName = it[AttendanceEntity.uniqueAttendanceId] ?: emptyString
                val attendanceDates = it[AttendanceEntity.dailyAttendances] ?: emptyString
                val absentDates = it[AttendanceEntity.absentDates] ?: emptyString
                val numberOfAttendedDays = it[AttendanceEntity.numberOfAttendedDays] ?: 0
                val numberOfAbsentDays = it[AttendanceEntity.numberOfAbsentDays] ?: 0
                val numberOfSchoolDays = it[AttendanceEntity.numberOfSchoolDays] ?: 0
                AttendanceResponse(uniqueAttendanceId, uniqueStudentId, uniqueClassId, uniqueTermName, attendanceDates, absentDates, numberOfAttendedDays, numberOfAbsentDays, numberOfSchoolDays)
            }
        return attendance.ifEmpty { null }
    }

    override suspend fun addDailyAttendance(dailyAttendance: String, uniqueAttendanceId: String) {
        db.update(AttendanceEntity){
            set(it.dailyAttendances, dailyAttendance)
            where { it.uniqueAttendanceId eq uniqueAttendanceId }
        }
    }

    override suspend fun addAttendance(attendance: AttendanceRequest, uniqueAttendanceId: String): Int {
        return db.insert(AttendanceEntity){
            set(it.uniqueAttendanceId, attendance.uniqueAttendanceId)
            set(it.uniqueClassId, attendance.uniqueClassId)
            set(it.uniqueStudentId, attendance.uniqueStudentId)
            set(it.uniqueTermName, attendance.uniqueTermName)
            set(it.numberOfAttendedDays, attendance.numberOfAttendedDays)
            set(it.numberOfAbsentDays, attendance.numberOfAbsentDays)
            set(it.numberOfSchoolDays, attendance.numberOfSchoolDays)
            set(it.dailyAttendances, attendance.dailyAttendances)
            set(it.absentDates, attendance.absentDates)
        }
    }

    override suspend fun getAttendance(uniqueAttendanceId: String): AttendanceResponse? {
        return db.from(AttendanceEntity)
            .select()
            .where{ AttendanceEntity.uniqueAttendanceId eq uniqueAttendanceId}
            .map {
                val uniqueClassId = it[AttendanceEntity.uniqueAttendanceId] ?: emptyString
                val uniqueStudentId = it[AttendanceEntity.uniqueAttendanceId] ?: emptyString
                val uniqueTermName = it[AttendanceEntity.uniqueAttendanceId] ?: emptyString
                val attendanceDates = it[AttendanceEntity.dailyAttendances] ?: emptyString
                val absentDates = it[AttendanceEntity.absentDates] ?: emptyString
                val numberOfAttendedDays = it[AttendanceEntity.numberOfAttendedDays] ?: 0
                val numberOfAbsentDays = it[AttendanceEntity.numberOfAbsentDays] ?: 0
                val numberOfSchoolDays = it[AttendanceEntity.numberOfSchoolDays] ?: 0
                AttendanceResponse(uniqueAttendanceId, uniqueStudentId, uniqueClassId, uniqueTermName, attendanceDates, absentDates, numberOfAttendedDays, numberOfAbsentDays, numberOfSchoolDays)
            }.firstOrNull()
    }

    override suspend fun updateAttendance(uniqueAttendanceId: String, updatedAttendance: AttendanceRequest): AttendanceResponse? {
        val rowsAffected = db.update(AttendanceEntity){
            set(it.uniqueAttendanceId, updatedAttendance.uniqueAttendanceId)
            set(it.uniqueClassId, updatedAttendance.uniqueClassId)
            set(it.uniqueStudentId, updatedAttendance.uniqueStudentId)
            set(it.uniqueTermName, updatedAttendance.uniqueTermName)
            set(it.numberOfAttendedDays, updatedAttendance.numberOfAttendedDays)
            set(it.numberOfAbsentDays, updatedAttendance.numberOfAbsentDays)
            set(it.numberOfSchoolDays, updatedAttendance.numberOfSchoolDays)
            set(it.dailyAttendances, updatedAttendance.dailyAttendances)
            set(it.absentDates, updatedAttendance.absentDates)
            where { it.uniqueAttendanceId eq uniqueAttendanceId }
        }
        return if (rowsAffected < 1){
            null
        }else {
            db.from(AttendanceEntity)
                .select()
                .where { AttendanceEntity.uniqueAttendanceId eq uniqueAttendanceId }
                .map {
                    val uniqueClassId = it[AttendanceEntity.uniqueAttendanceId] ?: emptyString
                    val uniqueStudentId = it[AttendanceEntity.uniqueAttendanceId] ?: emptyString
                    val uniqueTermName = it[AttendanceEntity.uniqueAttendanceId] ?: emptyString
                    val attendanceDates = it[AttendanceEntity.dailyAttendances] ?: emptyString
                    val absentDates = it[AttendanceEntity.absentDates] ?: emptyString
                    val numberOfAttendedDays = it[AttendanceEntity.numberOfAttendedDays] ?: 0
                    val numberOfAbsentDays = it[AttendanceEntity.numberOfAbsentDays] ?: 0
                    val numberOfSchoolDays = it[AttendanceEntity.numberOfSchoolDays] ?: 0
                    AttendanceResponse(uniqueAttendanceId, uniqueStudentId, uniqueClassId, uniqueTermName, attendanceDates, absentDates, numberOfAttendedDays, numberOfAbsentDays, numberOfSchoolDays)
                }.firstOrNull()
        }
    }

    override suspend fun deleteAttendance(uniqueAttendanceId: String): Int {
        return db.delete(AttendanceEntity) {
            AttendanceEntity.uniqueAttendanceId eq uniqueAttendanceId
        }
    }

}