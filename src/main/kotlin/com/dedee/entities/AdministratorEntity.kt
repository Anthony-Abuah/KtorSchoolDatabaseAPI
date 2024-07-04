package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import util.Constants.AdministratorTable
import util.DatabaseEntityFields.AdminUsername
import util.DatabaseEntityFields.AdministratorId
import util.DatabaseEntityFields.Email
import util.DatabaseEntityFields.FirstName
import util.DatabaseEntityFields.Gender
import util.DatabaseEntityFields.LastName
import util.DatabaseEntityFields.Password
import util.DatabaseEntityFields.Position
import util.DatabaseEntityFields.Salt

object AdministratorEntity: Table<Nothing>(AdministratorTable) {
    val administratorId = int(AdministratorId).primaryKey()
    val firstName = varchar(FirstName)
    val lastName = varchar(LastName)
    val adminUsername = varchar(AdminUsername)
    val password = varchar(Password)
    val salt = varchar(Salt)
    val email = varchar(Email)
    val gender = varchar(Gender)
    val position = varchar(Position)
}