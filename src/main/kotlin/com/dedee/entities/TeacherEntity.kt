package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import util.Constants.TeacherTable
import util.DatabaseEntityFields.Classes
import util.DatabaseEntityFields.Contact
import util.DatabaseEntityFields.Email
import util.DatabaseEntityFields.FirstName
import util.DatabaseEntityFields.Gender
import util.DatabaseEntityFields.IsStillAtThisSchool
import util.DatabaseEntityFields.LastName
import util.DatabaseEntityFields.OtherNames
import util.DatabaseEntityFields.TeacherId
import util.DatabaseEntityFields.TeacherUsername
import util.DatabaseEntityFields.Password
import util.DatabaseEntityFields.Salt
import util.DatabaseEntityFields.Students
import util.DatabaseEntityFields.Subjects
import util.DatabaseEntityFields.UniqueTeacherId

object TeacherEntity: Table<Nothing>(TeacherTable) {
    val firstName = varchar(FirstName)
    val lastName = varchar(LastName)
    val otherNames = varchar(OtherNames)
    val uniqueTeacherId = varchar(UniqueTeacherId).primaryKey()
    val teacherUsername = varchar(TeacherUsername)
    val password = varchar(Password)
    val salt = varchar(Salt)
    val email = varchar(Email)
    val gender = varchar(Gender)
    val contact = varchar(Contact)
    val isStillAtThisSchool = boolean(IsStillAtThisSchool)
    val subjects = varchar(Subjects)
    val classes = varchar(Classes)
    val students = varchar(Students)
}