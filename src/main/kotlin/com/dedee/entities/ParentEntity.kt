package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.varchar
import util.Constants.ParentTable
import util.DatabaseEntityFields.Contact
import util.DatabaseEntityFields.Email
import util.DatabaseEntityFields.FirstName
import util.DatabaseEntityFields.Gender
import util.DatabaseEntityFields.HasAnEnrolledWard
import util.DatabaseEntityFields.LastName
import util.DatabaseEntityFields.ParentUsername
import util.DatabaseEntityFields.Password
import util.DatabaseEntityFields.Salt
import util.DatabaseEntityFields.Wards

object ParentEntity: Table<Nothing>(ParentTable) {
    val firstName = varchar(FirstName)
    val lastName = varchar(LastName)
    val parentUsername = varchar(ParentUsername).primaryKey()
    val password = varchar(Password)
    val salt = varchar(Salt)
    val email = varchar(Email)
    val gender = varchar(Gender)
    val contact = varchar(Contact)
    val hasAnEnrolledWard = boolean(HasAnEnrolledWard)
    val wards = varchar(Wards)
}