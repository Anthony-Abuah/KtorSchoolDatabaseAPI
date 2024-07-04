package com.dedee.entities

import org.ktorm.schema.*
import util.Constants.DailyAttendanceTable
import util.Constants.ScoreTable
import util.DatabaseEntityFields.ClassAverage
import util.DatabaseEntityFields.Date
import util.DatabaseEntityFields.Grade
import util.DatabaseEntityFields.IsPresent
import util.DatabaseEntityFields.Marks
import util.DatabaseEntityFields.Remarks
import util.DatabaseEntityFields.ScoreType
import util.DatabaseEntityFields.UniqueAttendanceId
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueDailyAttendanceId
import util.DatabaseEntityFields.UniqueGradingId
import util.DatabaseEntityFields.UniqueScoreId
import util.DatabaseEntityFields.UniqueStudentId
import util.DatabaseEntityFields.UniqueSubjectId
import util.DatabaseEntityFields.UniqueTermName

object DailyAttendanceEntity: Table<Nothing>(DailyAttendanceTable) {
    val uniqueDailyAttendanceId = varchar(UniqueDailyAttendanceId).primaryKey()
    val uniqueAttendanceId = varchar(UniqueAttendanceId)
    val uniqueStudentId = varchar(UniqueStudentId)
    val uniqueClassId = varchar(UniqueClassId)
    val uniqueTermName = varchar(UniqueTermName)
    val date = int(Date)
    val isPresent = boolean(IsPresent)

}