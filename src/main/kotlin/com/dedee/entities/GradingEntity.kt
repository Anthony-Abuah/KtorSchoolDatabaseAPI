package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import util.Constants.GradingTable
import util.Constants.TermTable
import util.DatabaseEntityFields
import util.DatabaseEntityFields.FinalGrade
import util.DatabaseEntityFields.GradeRemarks
import util.DatabaseEntityFields.Scores
import util.DatabaseEntityFields.TeacherRemarks
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueGradingId
import util.DatabaseEntityFields.UniqueStudentId
import util.DatabaseEntityFields.UniqueSubjectId
import util.DatabaseEntityFields.UniqueTeacherId
import util.DatabaseEntityFields.UniqueTermName

object GradingEntity: Table<Nothing>(GradingTable) {
    val uniqueGradingId = varchar(UniqueGradingId).primaryKey()
    val uniqueStudentId = varchar(UniqueStudentId)
    val uniqueSubjectId = varchar(UniqueSubjectId)
    val uniqueTeacherId = varchar(UniqueTeacherId)
    val uniqueClassId = varchar(UniqueClassId)
    val uniqueTermName = varchar(UniqueTermName)
    val finalGrade = varchar(FinalGrade)
    val teacherRemarks = varchar(TeacherRemarks)
    val gradeRemarks = varchar(GradeRemarks)
    val scores = varchar(Scores)

}