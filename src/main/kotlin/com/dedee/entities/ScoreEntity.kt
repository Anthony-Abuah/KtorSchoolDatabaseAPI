package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.double
import org.ktorm.schema.varchar
import util.Constants.ScoreTable
import util.DatabaseEntityFields.ClassAverage
import util.DatabaseEntityFields.Grade
import util.DatabaseEntityFields.Marks
import util.DatabaseEntityFields.Remarks
import util.DatabaseEntityFields.ScoreType
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueGradingId
import util.DatabaseEntityFields.UniqueScoreId
import util.DatabaseEntityFields.UniqueStudentId
import util.DatabaseEntityFields.UniqueSubjectId
import util.DatabaseEntityFields.UniqueTermName

object ScoreEntity: Table<Nothing>(ScoreTable) {
    val uniqueGradingId = varchar(UniqueGradingId)
    val uniqueStudentId = varchar(UniqueStudentId)
    val uniqueSubjectId = varchar(UniqueSubjectId)
    val uniqueScoreId = varchar(UniqueScoreId).primaryKey()
    val uniqueClassId = varchar(UniqueClassId)
    val uniqueTermName = varchar(UniqueTermName)
    val scoreType = varchar(ScoreType)
    val marks = double(Marks)
    val grade = varchar(Grade)
    val remarks = varchar(Remarks)
    val classAverage = double(ClassAverage)

}