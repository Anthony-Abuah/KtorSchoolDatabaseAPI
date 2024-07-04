package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import util.Constants.ScoreTable
import util.DatabaseEntityFields.Days
import util.DatabaseEntityFields.EndTime
import util.DatabaseEntityFields.StartTime
import util.DatabaseEntityFields.TimeTableType
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueSubjectId
import util.DatabaseEntityFields.UniqueTeacherId
import util.DatabaseEntityFields.UniqueTermName
import util.DatabaseEntityFields.UniqueTimeTableId

object TimeTableEntity: Table<Nothing>(ScoreTable) {
    val uniqueTimeTableId = varchar(UniqueTimeTableId).primaryKey()
    val uniqueSubjectId = varchar(UniqueSubjectId)
    val uniqueClassId = varchar(UniqueClassId)
    val uniqueTeacherId = varchar(UniqueTeacherId)
    val uniqueTermName = varchar(UniqueTermName)
    val timeTableType = varchar(TimeTableType)
    val days = varchar(Days)
    val startTime = varchar(StartTime)
    val endTime = varchar(EndTime)

}