package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import util.Constants.StudentParentTable
import util.DatabaseEntityFields.ParentUsername
import util.DatabaseEntityFields.UniqueStudentId

object StudentParentEntity: Table<Nothing>(StudentParentTable) {
    val uniqueStudentId = varchar(UniqueStudentId).primaryKey()
    val parentUsername = varchar(ParentUsername).primaryKey()

}