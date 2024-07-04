package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import util.Constants.TermTable
import util.DatabaseEntityFields.EndDate
import util.DatabaseEntityFields.NumberOfDays
import util.DatabaseEntityFields.StartDate
import util.DatabaseEntityFields.TermName
import util.DatabaseEntityFields.UniqueTermName
import util.DatabaseEntityFields.Year

object TermEntity: Table<Nothing>(TermTable) {
    val uniqueTermName = varchar(UniqueTermName).primaryKey()
    val termName = varchar(TermName)
    val year = int(Year)
    val startDate = int(StartDate)
    val endDate = int(EndDate)
    val numberOfDays = int(NumberOfDays)
}