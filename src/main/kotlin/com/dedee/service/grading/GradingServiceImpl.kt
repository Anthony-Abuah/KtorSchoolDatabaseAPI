package com.dedee.service.grading

import com.dedee.entities.GradingEntity
import com.dedee.model.grading.GradingRequest
import com.dedee.model.grading.GradingResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString

class GradingServiceImpl(
    private val db: Database,
) : GradingService{
    override suspend fun getAllGradings(): List<GradingResponse>? {
        val grading = db.from(GradingEntity).select()
            .map {
                val uniqueGradingId = it[GradingEntity.uniqueGradingId] ?: emptyString
                val uniqueStudentId = it[GradingEntity.uniqueStudentId] ?: emptyString
                val uniqueSubjectId = it[GradingEntity.uniqueSubjectId] ?: emptyString
                val uniqueClassId = it[GradingEntity.uniqueClassId] ?: emptyString
                val uniqueTeacherId = it[GradingEntity.uniqueTeacherId] ?: emptyString
                val uniqueTermName = it[GradingEntity.uniqueTermName] ?: emptyString
                val finalGrade = it[GradingEntity.finalGrade] ?: emptyString
                val gradeRemarks = it[GradingEntity.gradeRemarks] ?: emptyString
                val teacherRemarks = it[GradingEntity.teacherRemarks] ?: emptyString
                val scores = it[GradingEntity.scores] ?: emptyString
                GradingResponse(uniqueGradingId, uniqueStudentId, uniqueSubjectId, uniqueClassId, uniqueTeacherId, uniqueTermName, finalGrade, teacherRemarks, gradeRemarks, scores)
            }
        return grading.ifEmpty { null }
    }

    override suspend fun addScore(scores: String, uniqueGradingId: String) {
        db.update(GradingEntity){
            set(it.scores, scores)
            where { it.uniqueGradingId eq uniqueGradingId }
        }
    }

    override suspend fun addGrading(grading: GradingRequest, uniqueGradingId: String): Int {
        return db.insert(GradingEntity){
            set(it.uniqueGradingId, uniqueGradingId)
            set(it.uniqueGradingId, grading.uniqueStudentId)
            set(it.uniqueSubjectId, grading.uniqueSubjectId)
            set(it.uniqueClassId, grading.uniqueClassId)
            set(it.uniqueTeacherId, grading.uniqueTeacherId)
            set(it.uniqueTermName, grading.uniqueTermName)
            set(it.teacherRemarks, grading.teacherRemarks)
            set(it.gradeRemarks, grading.gradeRemarks)
            set(it.finalGrade, grading.finalGrade)
        }
    }

    override suspend fun getGrading(uniqueGradingId: String): GradingResponse? {
        return db.from(GradingEntity)
            .select()
            .where{ GradingEntity.uniqueGradingId eq uniqueGradingId}
            .map {
                val uniqueStudentId = it[GradingEntity.uniqueStudentId] ?: emptyString
                val uniqueSubjectId = it[GradingEntity.uniqueSubjectId] ?: emptyString
                val uniqueClassId = it[GradingEntity.uniqueClassId] ?: emptyString
                val uniqueTeacherId = it[GradingEntity.uniqueTeacherId] ?: emptyString
                val uniqueTermName = it[GradingEntity.uniqueTermName] ?: emptyString
                val finalGrade = it[GradingEntity.finalGrade] ?: emptyString
                val gradeRemarks = it[GradingEntity.gradeRemarks] ?: emptyString
                val teacherRemarks = it[GradingEntity.teacherRemarks] ?: emptyString
                val scores = it[GradingEntity.scores] ?: emptyString
                GradingResponse(uniqueGradingId, uniqueStudentId, uniqueSubjectId, uniqueClassId, uniqueTeacherId, uniqueTermName, finalGrade, teacherRemarks, gradeRemarks, scores)
            }.firstOrNull()
    }

    override suspend fun getClassGradings(uniqueClassId: String, uniqueSubjectId: String, uniqueTermName: String): List<GradingResponse> {
        return db.from(GradingEntity)
            .select()
            .where{ GradingEntity.uniqueClassId eq uniqueClassId}
            .where { GradingEntity.uniqueSubjectId eq uniqueSubjectId }
            .where { GradingEntity.uniqueTermName eq uniqueTermName }
            .map {
                val uniqueGradingId = it[GradingEntity.uniqueGradingId] ?: emptyString
                val uniqueStudentId = it[GradingEntity.uniqueStudentId] ?: emptyString
                val uniqueTeacherId = it[GradingEntity.uniqueTeacherId] ?: emptyString
                val finalGrade = it[GradingEntity.finalGrade] ?: emptyString
                val gradeRemarks = it[GradingEntity.gradeRemarks] ?: emptyString
                val teacherRemarks = it[GradingEntity.teacherRemarks] ?: emptyString
                val scores = it[GradingEntity.scores] ?: emptyString
                GradingResponse(uniqueGradingId, uniqueStudentId, uniqueSubjectId, uniqueClassId, uniqueTeacherId, uniqueTermName, finalGrade, teacherRemarks, gradeRemarks, scores)
            }


    }

    override suspend fun getStudentGradings(uniqueStudentId: String, uniqueClassId: String, uniqueSubjectId: String?, uniqueTermName: String?): List<GradingResponse> {
        if (uniqueSubjectId != null && uniqueTermName != null){
            return db.from(GradingEntity)
                .select()
                .where{ GradingEntity.uniqueStudentId eq uniqueStudentId}
                .where { GradingEntity.uniqueClassId eq uniqueClassId }
                .where { GradingEntity.uniqueSubjectId eq uniqueSubjectId }
                .where { GradingEntity.uniqueTermName eq uniqueTermName }
                .map {
                    val uniqueGradingId = it[GradingEntity.uniqueGradingId] ?: emptyString
                    val uniqueTeacherId = it[GradingEntity.uniqueTeacherId] ?: emptyString
                    val thisUniqueSubjectId = it[GradingEntity.uniqueSubjectId] ?: emptyString
                    val thisUniqueTermName = it[GradingEntity.uniqueTermName] ?: emptyString
                    val finalGrade = it[GradingEntity.finalGrade] ?: emptyString
                    val gradeRemarks = it[GradingEntity.gradeRemarks] ?: emptyString
                    val teacherRemarks = it[GradingEntity.teacherRemarks] ?: emptyString
                    val scores = it[GradingEntity.scores] ?: emptyString
                    GradingResponse(uniqueGradingId, uniqueStudentId, thisUniqueSubjectId, uniqueClassId, uniqueTeacherId, thisUniqueTermName, finalGrade, teacherRemarks, gradeRemarks, scores)
                }
        }else if (uniqueSubjectId != null){
            return db.from(GradingEntity)
                .select()
                .where{ GradingEntity.uniqueStudentId eq uniqueStudentId}
                .where { GradingEntity.uniqueClassId eq uniqueClassId }
                .where { GradingEntity.uniqueSubjectId eq uniqueSubjectId }
                .map {
                    val uniqueGradingId = it[GradingEntity.uniqueGradingId] ?: emptyString
                    val uniqueTeacherId = it[GradingEntity.uniqueTeacherId] ?: emptyString
                    val thisUniqueSubjectId = it[GradingEntity.uniqueSubjectId] ?: emptyString
                    val thisUniqueTermName = it[GradingEntity.uniqueTermName] ?: emptyString
                    val finalGrade = it[GradingEntity.finalGrade] ?: emptyString
                    val gradeRemarks = it[GradingEntity.gradeRemarks] ?: emptyString
                    val teacherRemarks = it[GradingEntity.teacherRemarks] ?: emptyString
                    val scores = it[GradingEntity.scores] ?: emptyString
                    GradingResponse(uniqueGradingId, uniqueStudentId, thisUniqueSubjectId, uniqueClassId, uniqueTeacherId, thisUniqueTermName, finalGrade, teacherRemarks, gradeRemarks, scores)
                }
        }else if (uniqueTermName != null){
            return db.from(GradingEntity)
                .select()
                .where{ GradingEntity.uniqueStudentId eq uniqueStudentId}
                .where { GradingEntity.uniqueClassId eq uniqueClassId }
                .where { GradingEntity.uniqueTermName eq uniqueTermName }
                .map {
                    val uniqueGradingId = it[GradingEntity.uniqueGradingId] ?: emptyString
                    val uniqueTeacherId = it[GradingEntity.uniqueTeacherId] ?: emptyString
                    val thisUniqueSubjectId = it[GradingEntity.uniqueSubjectId] ?: emptyString
                    val thisUniqueTermName = it[GradingEntity.uniqueTermName] ?: emptyString
                    val finalGrade = it[GradingEntity.finalGrade] ?: emptyString
                    val gradeRemarks = it[GradingEntity.gradeRemarks] ?: emptyString
                    val teacherRemarks = it[GradingEntity.teacherRemarks] ?: emptyString
                    val scores = it[GradingEntity.scores] ?: emptyString
                    GradingResponse(uniqueGradingId, uniqueStudentId, thisUniqueSubjectId, uniqueClassId, uniqueTeacherId, thisUniqueTermName, finalGrade, teacherRemarks, gradeRemarks, scores)
                }
        }else{
            return db.from(GradingEntity)
                .select()
                .where{ GradingEntity.uniqueStudentId eq uniqueStudentId}
                .where { GradingEntity.uniqueClassId eq uniqueClassId }
                .map {
                    val uniqueGradingId = it[GradingEntity.uniqueGradingId] ?: emptyString
                    val uniqueTeacherId = it[GradingEntity.uniqueTeacherId] ?: emptyString
                    val thisUniqueSubjectId = it[GradingEntity.uniqueSubjectId] ?: emptyString
                    val thisUniqueTermName = it[GradingEntity.uniqueTermName] ?: emptyString
                    val finalGrade = it[GradingEntity.finalGrade] ?: emptyString
                    val gradeRemarks = it[GradingEntity.gradeRemarks] ?: emptyString
                    val teacherRemarks = it[GradingEntity.teacherRemarks] ?: emptyString
                    val scores = it[GradingEntity.scores] ?: emptyString
                    GradingResponse(uniqueGradingId, uniqueStudentId, thisUniqueSubjectId, uniqueClassId, uniqueTeacherId, thisUniqueTermName, finalGrade, teacherRemarks, gradeRemarks, scores)
                }
        }
    }

    override suspend fun updateGrading(uniqueGradingId: String, updatedGrading: GradingRequest): GradingResponse? {
        val rowsAffected = db.update(GradingEntity){
            set(it.uniqueGradingId, uniqueGradingId)
            set(it.uniqueGradingId, updatedGrading.uniqueStudentId)
            set(it.uniqueSubjectId, updatedGrading.uniqueSubjectId)
            set(it.uniqueClassId, updatedGrading.uniqueClassId)
            set(it.uniqueTeacherId, updatedGrading.uniqueTeacherId)
            set(it.uniqueTermName, updatedGrading.uniqueTermName)
            set(it.teacherRemarks, updatedGrading.teacherRemarks)
            set(it.gradeRemarks, updatedGrading.gradeRemarks)
            set(it.finalGrade, updatedGrading.finalGrade)
            where { it.uniqueGradingId eq uniqueGradingId }
        }
        return if (rowsAffected < 1){
            null
        }else {
            db.from(GradingEntity)
                .select()
                .where { GradingEntity.uniqueGradingId eq uniqueGradingId }
                .map {
                    val uniqueStudentId = it[GradingEntity.uniqueStudentId] ?: emptyString
                    val uniqueSubjectId = it[GradingEntity.uniqueSubjectId] ?: emptyString
                    val uniqueClassId = it[GradingEntity.uniqueClassId] ?: emptyString
                    val uniqueTeacherId = it[GradingEntity.uniqueTeacherId] ?: emptyString
                    val uniqueTermName = it[GradingEntity.uniqueTermName] ?: emptyString
                    val finalGrade = it[GradingEntity.finalGrade] ?: emptyString
                    val gradeRemarks = it[GradingEntity.gradeRemarks] ?: emptyString
                    val teacherRemarks = it[GradingEntity.teacherRemarks] ?: emptyString
                    val scores = it[GradingEntity.scores] ?: emptyString
                    GradingResponse(uniqueGradingId, uniqueStudentId, uniqueSubjectId, uniqueClassId, uniqueTeacherId, uniqueTermName, finalGrade, teacherRemarks, gradeRemarks, scores)
                }.firstOrNull()
        }
    }

    override suspend fun deleteGrading(uniqueGradingId: String): Int {
        return db.delete(GradingEntity) {
            GradingEntity.uniqueGradingId eq uniqueGradingId
        }
    }

}