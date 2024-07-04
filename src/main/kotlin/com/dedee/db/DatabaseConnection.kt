package com.dedee.db

import org.ktorm.database.Database
import util.Constants.databaseName

object DatabaseConnection{

    val database = Database.connect(
        url = "jdbc:mysql://localhost:3307/$databaseName",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = "IloveDEDEE1997"
    )

}