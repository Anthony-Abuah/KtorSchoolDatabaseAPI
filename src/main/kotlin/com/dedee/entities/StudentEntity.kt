package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import util.Constants.StudentTable
import util.DatabaseEntityFields.Classes
import util.DatabaseEntityFields.Conducts
import util.DatabaseEntityFields.Contact
import util.DatabaseEntityFields.DateOfBirth
import util.DatabaseEntityFields.Email
import util.DatabaseEntityFields.FirstName
import util.DatabaseEntityFields.Gender
import util.DatabaseEntityFields.Gradings
import util.DatabaseEntityFields.IsEnrolled
import util.DatabaseEntityFields.LastName
import util.DatabaseEntityFields.OtherNames
import util.DatabaseEntityFields.Parents
import util.DatabaseEntityFields.Photo
import util.DatabaseEntityFields.Subjects
import util.DatabaseEntityFields.Teachers
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueStudentId

object StudentEntity: Table<Nothing>(StudentTable) {
    val firstName = varchar(FirstName)
    val lastName = varchar(LastName)
    val otherNames = varchar(OtherNames)
    val uniqueStudentId = varchar(UniqueStudentId).primaryKey()
    val uniqueClassId = varchar(UniqueClassId)
    val dateOfBirth = int(DateOfBirth)
    val email = varchar(Email)
    val photo = varchar(Photo)
    val gender = varchar(Gender)
    val isEnrolled = boolean(IsEnrolled)
    val teachers = varchar(Teachers)
    val parents = varchar(Parents)
    val subjects = varchar(Subjects)
    val classes = varchar(Classes)
    val gradings = varchar(Gradings)
    val conducts = varchar(Conducts)
}