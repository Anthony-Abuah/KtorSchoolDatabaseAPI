package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import util.Constants.AttendanceTable
import util.DatabaseEntityFields.AbsentDates
import util.DatabaseEntityFields.DailyAttendances
import util.DatabaseEntityFields.NumberOfAbsentDays
import util.DatabaseEntityFields.NumberOfAttendedDays
import util.DatabaseEntityFields.NumberOfSchoolDays
import util.DatabaseEntityFields.UniqueAttendanceId
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueStudentId
import util.DatabaseEntityFields.UniqueTermName

object AttendanceEntity: Table<Nothing>(AttendanceTable) {
    val uniqueAttendanceId = varchar(UniqueAttendanceId).primaryKey()
    val uniqueStudentId = varchar(UniqueStudentId)
    val uniqueClassId = varchar(UniqueClassId)
    val uniqueTermName = varchar(UniqueTermName)
    val dailyAttendances = varchar(DailyAttendances)
    val absentDates = varchar(AbsentDates)
    val numberOfAttendedDays = int(NumberOfAttendedDays)
    val numberOfAbsentDays = int(NumberOfAbsentDays)
    val numberOfSchoolDays = int(NumberOfSchoolDays)
}


