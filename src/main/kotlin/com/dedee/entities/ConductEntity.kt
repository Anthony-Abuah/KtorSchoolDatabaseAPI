package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import util.Constants.ConductTable
import util.DatabaseEntityFields.Conduct
import util.DatabaseEntityFields.Interests
import util.DatabaseEntityFields.UniqueAttendanceId
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueConductId
import util.DatabaseEntityFields.UniqueStudentId
import util.DatabaseEntityFields.UniqueTeacherId
import util.DatabaseEntityFields.UniqueTermName

object ConductEntity: Table<Nothing>(ConductTable) {
    val uniqueConductId = varchar(UniqueConductId).primaryKey()
    val uniqueAttendanceId = varchar(UniqueAttendanceId)
    val uniqueStudentId = varchar(UniqueStudentId)
    val uniqueTeacherId = varchar(UniqueTeacherId)
    val uniqueClassId = varchar(UniqueClassId)
    val uniqueTermName = varchar(UniqueTermName)
    val conduct = varchar(Conduct)
    val interests = varchar(Interests)
}