package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import util.Constants.TeacherStudentTable
import util.DatabaseEntityFields.UniqueStudentId
import util.DatabaseEntityFields.UniqueTeacherId

object TeacherStudentEntity: Table<Nothing>(TeacherStudentTable) {
    val uniqueStudentId = varchar(UniqueStudentId).primaryKey()
    val uniqueTeacherId = varchar(UniqueTeacherId).primaryKey()
}