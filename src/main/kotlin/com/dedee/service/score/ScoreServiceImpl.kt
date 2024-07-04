package com.dedee.service.score

import com.dedee.entities.ScoreEntity
import com.dedee.model.score.ScoreRequest
import com.dedee.model.score.ScoreResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString

class ScoreServiceImpl(
    private val db: Database,
) : ScoreService{
    override suspend fun getAllScores(): List<ScoreResponse>? {
        val score = db.from(ScoreEntity).select()
            .map {
                val uniqueScoreId = it[ScoreEntity.uniqueScoreId] ?: emptyString
                val uniqueStudentId = it[ScoreEntity.uniqueStudentId] ?: emptyString
                val uniqueGradingId = it[ScoreEntity.uniqueGradingId] ?: emptyString
                val uniqueSubjectId = it[ScoreEntity.uniqueSubjectId] ?: emptyString
                val uniqueClassId = it[ScoreEntity.uniqueClassId] ?: emptyString
                val uniqueTermName = it[ScoreEntity.uniqueTermName] ?: emptyString
                val scoreType = it[ScoreEntity.scoreType] ?: emptyString
                val marks = it[ScoreEntity.marks] ?: 0.0
                val grade = it[ScoreEntity.grade] ?: emptyString
                val remarks = it[ScoreEntity.remarks] ?: emptyString
                val classAverage = it[ScoreEntity.classAverage] ?: 0.0
                ScoreResponse(uniqueScoreId, uniqueGradingId, uniqueStudentId, uniqueSubjectId, uniqueClassId, uniqueTermName, scoreType, marks, grade, remarks, classAverage)
            }
        return score.ifEmpty { null }
    }

    override suspend fun addScore(score: ScoreRequest, uniqueScoreId: String): Int {
        return db.insert(ScoreEntity){
            set(it.uniqueScoreId, uniqueScoreId)
            set(it.uniqueGradingId, score.uniqueGradingId)
            set(it.uniqueScoreId, score.uniqueStudentId)
            set(it.uniqueSubjectId, score.uniqueSubjectId)
            set(it.uniqueClassId, score.uniqueClassId)
            set(it.uniqueTermName, score.uniqueTermName)
            set(it.scoreType, score.scoreType)
            set(it.grade, score.grade)
            set(it.marks, score.marks)
            set(it.remarks, score.remarks)
            set(it.classAverage, score.classAverage)
        }
    }

    override suspend fun getScore(uniqueScoreId: String): ScoreResponse? {
        return db.from(ScoreEntity)
            .select()
            .where{ ScoreEntity.uniqueScoreId eq uniqueScoreId}
            .map {
                val uniqueStudentId = it[ScoreEntity.uniqueStudentId] ?: emptyString
                val uniqueGradingId = it[ScoreEntity.uniqueGradingId] ?: emptyString
                val uniqueSubjectId = it[ScoreEntity.uniqueSubjectId] ?: emptyString
                val uniqueClassId = it[ScoreEntity.uniqueClassId] ?: emptyString
                val uniqueTermName = it[ScoreEntity.uniqueTermName] ?: emptyString
                val scoreType = it[ScoreEntity.scoreType] ?: emptyString
                val marks = it[ScoreEntity.marks] ?: 0.0
                val grade = it[ScoreEntity.grade] ?: emptyString
                val remarks = it[ScoreEntity.remarks] ?: emptyString
                val classAverage = it[ScoreEntity.classAverage] ?: 0.0
                ScoreResponse(uniqueScoreId, uniqueGradingId, uniqueStudentId, uniqueSubjectId, uniqueClassId, uniqueTermName, scoreType, marks, grade, remarks, classAverage)
            }.firstOrNull()
    }


    override suspend fun updateScore(uniqueScoreId: String, updatedScore: ScoreRequest): ScoreResponse? {
        val rowsAffected = db.update(ScoreEntity){
            set(it.uniqueScoreId, uniqueScoreId)
            set(it.uniqueGradingId, updatedScore.uniqueGradingId)
            set(it.uniqueScoreId, updatedScore.uniqueStudentId)
            set(it.uniqueSubjectId, updatedScore.uniqueSubjectId)
            set(it.uniqueClassId, updatedScore.uniqueClassId)
            set(it.uniqueTermName, updatedScore.uniqueTermName)
            set(it.scoreType, updatedScore.scoreType)
            set(it.grade, updatedScore.grade)
            set(it.marks, updatedScore.marks)
            set(it.remarks, updatedScore.remarks)
            set(it.classAverage, updatedScore.classAverage)
            where { it.uniqueScoreId eq uniqueScoreId }
        }
        return if (rowsAffected < 1){
            null
        }else {
            db.from(ScoreEntity)
                .select()
                .where { ScoreEntity.uniqueScoreId eq uniqueScoreId }
                .map {
                    val uniqueStudentId = it[ScoreEntity.uniqueStudentId] ?: emptyString
                    val uniqueGradingId = it[ScoreEntity.uniqueGradingId] ?: emptyString
                    val uniqueSubjectId = it[ScoreEntity.uniqueSubjectId] ?: emptyString
                    val uniqueClassId = it[ScoreEntity.uniqueClassId] ?: emptyString
                    val uniqueTermName = it[ScoreEntity.uniqueTermName] ?: emptyString
                    val scoreType = it[ScoreEntity.scoreType] ?: emptyString
                    val marks = it[ScoreEntity.marks] ?: 0.0
                    val grade = it[ScoreEntity.grade] ?: emptyString
                    val remarks = it[ScoreEntity.remarks] ?: emptyString
                    val classAverage = it[ScoreEntity.classAverage] ?: 0.0
                    ScoreResponse(uniqueScoreId, uniqueGradingId, uniqueStudentId, uniqueSubjectId, uniqueClassId, uniqueTermName, scoreType, marks, grade, remarks, classAverage)
                }.firstOrNull()
        }
    }

    override suspend fun deleteScore(uniqueScoreId: String): Int {
        return db.delete(ScoreEntity) {
            ScoreEntity.uniqueScoreId eq uniqueScoreId
        }
    }

}