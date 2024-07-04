package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import util.Constants.SubjectClassTable
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueSubjectId

object SubjectClassEntity: Table<Nothing>(SubjectClassTable) {
    val uniqueSubjectId = varchar(UniqueSubjectId).primaryKey()
    val uniqueClassId = varchar(UniqueClassId).primaryKey()
}