package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import util.Constants.SchoolClassTable
import util.DatabaseEntityFields.AcademicYear
import util.DatabaseEntityFields.ClassName
import util.DatabaseEntityFields.ClassStage
import util.DatabaseEntityFields.NumberOfBoys
import util.DatabaseEntityFields.NumberOfGirls
import util.DatabaseEntityFields.Students
import util.DatabaseEntityFields.Subjects
import util.DatabaseEntityFields.Teachers
import util.DatabaseEntityFields.TotalNumberOfStudents
import util.DatabaseEntityFields.UniqueClassId

object SchoolClassEntity: Table<Nothing>(SchoolClassTable) {
    val className = varchar(ClassName)
    val uniqueClassId = varchar(UniqueClassId).primaryKey()
    val academicYear = varchar(AcademicYear)
    val classStage = varchar(ClassStage)
    val numberOfBoys = int(NumberOfBoys)
    val numberOfGirls = int(NumberOfGirls)
    val totalNumberOfStudents = int(TotalNumberOfStudents)
    val students = varchar(Students)
    val teachers = varchar(Teachers)
    val subjects = varchar(Subjects)
}