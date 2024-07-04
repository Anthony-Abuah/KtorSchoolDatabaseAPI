package com.dedee.service.term

import com.dedee.entities.TermEntity
import com.dedee.model.term.TermRequest
import com.dedee.model.term.TermResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString
import util.DatabaseEntityFields.TermName

class TermServiceImpl(
    private val db: Database,
) : TermService{
    override suspend fun getAllTerms(): List<TermResponse>? {
        val term = db.from(TermEntity).select()
            .map {
                val termName = it[TermEntity.termName] ?: emptyString
                val uniqueTermName = it[TermEntity.uniqueTermName] ?: emptyString
                val year = it[TermEntity.year] ?: 0
                val startDate = it[TermEntity.startDate] ?: 0
                val endDate = it[TermEntity.endDate] ?: 0
                val numberOfDays = it[TermEntity.numberOfDays] ?: 0
                TermResponse(termName, uniqueTermName, year, startDate, endDate, numberOfDays)
            }
        return term.ifEmpty { null }
    }

    override suspend fun addTerm(term: TermRequest, uniqueTermName: String): Int {
        return db.insert(TermEntity){
            set(it.termName, term.termName)
            set(it.uniqueTermName, uniqueTermName)
            set(it.year, term.year)
            set(it.startDate, term.startDate)
            set(it.endDate, term.endDate)
            set(it.numberOfDays, term.numberOfDays)
        }
    }

    override suspend fun getTerm(parameterName: String, parameterType: String): List<TermResponse> {
        when(parameterType){
            TermName -> {
                return db.from(TermEntity)
                    .select()
                    .where{ TermEntity.termName eq parameterName}
                    .map {
                        val termName = it[TermEntity.termName] ?: emptyString
                        val uniqueTermName = it[TermEntity.uniqueTermName] ?: emptyString
                        val year = it[TermEntity.year] ?: 0
                        val startDate = it[TermEntity.startDate] ?: 0
                        val endDate = it[TermEntity.endDate] ?: 0
                        val numberOfDays = it[TermEntity.numberOfDays] ?: 0
                        TermResponse(termName, uniqueTermName, year, startDate, endDate, numberOfDays)
                    }
            }
            else -> {
                return db.from(TermEntity)
                    .select()
                    .where { TermEntity.uniqueTermName eq parameterName }
                    .map {
                        val termName = it[TermEntity.termName] ?: emptyString
                        val uniqueTermName = it[TermEntity.uniqueTermName] ?: emptyString
                        val year = it[TermEntity.year] ?: 0
                        val startDate = it[TermEntity.startDate] ?: 0
                        val endDate = it[TermEntity.endDate] ?: 0
                        val numberOfDays = it[TermEntity.numberOfDays] ?: 0
                        TermResponse(termName, uniqueTermName, year, startDate, endDate, numberOfDays)
                    }
            }
        }
    }

    override suspend fun updateTerm(uniqueTermName: String, updatedTerm: TermRequest): TermResponse? {
        val rowsAffected = db.update(TermEntity){
            set(it.termName, updatedTerm.termName)
            set(it.year, updatedTerm.year)
            set(it.startDate, updatedTerm.startDate)
            set(it.endDate, updatedTerm.endDate)
            set(it.numberOfDays, updatedTerm.numberOfDays)
            where { it.uniqueTermName eq uniqueTermName }
        }
        return if (rowsAffected < 1){
            null
        }else {
            db.from(TermEntity)
                .select()
                .where { TermEntity.uniqueTermName eq uniqueTermName }
                .map {
                    val termName = it[TermEntity.termName] ?: emptyString
                    val year = it[TermEntity.year] ?: 0
                    val startDate = it[TermEntity.startDate] ?: 0
                    val endDate = it[TermEntity.endDate] ?: 0
                    val numberOfDays = it[TermEntity.numberOfDays] ?: 0
                    TermResponse(termName, uniqueTermName, year, startDate, endDate, numberOfDays)
                }.firstOrNull()
        }
    }

    override suspend fun deleteTerm(uniqueTermName: String): Int {
        return db.delete(TermEntity) {
            TermEntity.uniqueTermName eq uniqueTermName
        }
    }

}