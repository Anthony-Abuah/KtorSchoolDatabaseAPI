package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import util.Constants.SubjectTable
import util.DatabaseEntityFields.Classes
import util.DatabaseEntityFields.SubjectName
import util.DatabaseEntityFields.Teachers
import util.DatabaseEntityFields.UniqueSubjectId

object SubjectEntity: Table<Nothing>(SubjectTable) {
    val subjectName = varchar(SubjectName)
    val uniqueSubjectId = varchar(UniqueSubjectId).primaryKey()
    val classes = varchar(Classes)
    val teachers = varchar(Teachers)
}