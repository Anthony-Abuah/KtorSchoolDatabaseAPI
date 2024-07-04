package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import util.Constants.TeacherSubjectTable
import util.DatabaseEntityFields.UniqueSubjectId
import util.DatabaseEntityFields.UniqueTeacherId

object TeacherSubjectEntity: Table<Nothing>(TeacherSubjectTable) {
    val uniqueSubjectId = varchar(UniqueSubjectId).primaryKey()
    val uniqueTeacherId = varchar(UniqueTeacherId).primaryKey()
}