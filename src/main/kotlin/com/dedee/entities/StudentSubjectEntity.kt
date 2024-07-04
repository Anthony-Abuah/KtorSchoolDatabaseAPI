package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import util.Constants.StudentSubjectTable
import util.DatabaseEntityFields.UniqueStudentId
import util.DatabaseEntityFields.UniqueSubjectId

object StudentSubjectEntity: Table<Nothing>(StudentSubjectTable) {
    val uniqueStudentId = varchar(UniqueStudentId).primaryKey()
    val uniqueSubjectId = varchar(UniqueSubjectId).primaryKey()
}