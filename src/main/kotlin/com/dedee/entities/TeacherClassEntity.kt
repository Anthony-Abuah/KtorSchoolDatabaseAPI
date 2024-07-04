package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import util.Constants.TeacherClassTable
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueTeacherId

object TeacherClassEntity: Table<Nothing>(TeacherClassTable) {
    val uniqueClassId = varchar(UniqueClassId).primaryKey()
    val uniqueTeacherId = varchar(UniqueTeacherId).primaryKey()
}