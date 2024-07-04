package com.dedee.service.subject

import com.dedee.entities.SubjectEntity
import com.dedee.model.subject.SubjectRequest
import com.dedee.model.subject.SubjectResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString
import util.DatabaseEntityFields.SubjectName

class SubjectServiceImpl(
    private val db: Database,
) : SubjectService{
    override suspend fun getAllSubjects(): List<SubjectResponse>? {
        val subject = db.from(SubjectEntity).select()
            .map {
                val subjectName = it[SubjectEntity.subjectName] ?: emptyString
                val uniqueSubjectId = it[SubjectEntity.uniqueSubjectId] ?: emptyString
                val teachers = it[SubjectEntity.teachers] ?: emptyString
                val classes = it[SubjectEntity.classes] ?: emptyString
                SubjectResponse(subjectName, uniqueSubjectId, classes, teachers)
            }
        return subject.ifEmpty { null }
    }

    override suspend fun addClasses(classes: String, uniqueSubjectId: String) {
        db.update(SubjectEntity){
            set(it.classes, classes)
            where { it.uniqueSubjectId eq uniqueSubjectId }
        }
    }

    override suspend fun addTeachers(teachers: String, uniqueSubjectId: String) {
        db.update(SubjectEntity){
            set(it.teachers, teachers)
            where { it.uniqueSubjectId eq uniqueSubjectId }
        }
    }

    override suspend fun addSubject(subject: SubjectRequest, uniqueSubjectId: String): Int {
        return db.insert(SubjectEntity){
            set(it.subjectName, subject.subjectName)
            set(it.uniqueSubjectId, uniqueSubjectId)
        }
    }

    override suspend fun getSubject(parameterName: String, parameterType: String): List<SubjectResponse> {
        when(parameterType){
            SubjectName -> {
                return db.from(SubjectEntity)
                    .select()
                    .where{ SubjectEntity.subjectName eq parameterName}
                    .map {
                        val subjectName = it[SubjectEntity.subjectName] ?: emptyString
                        val uniqueSubjectId = it[SubjectEntity.uniqueSubjectId] ?: emptyString
                        val teachers = it[SubjectEntity.teachers] ?: emptyString
                        val classes = it[SubjectEntity.classes] ?: emptyString
                        SubjectResponse(subjectName, uniqueSubjectId, classes, teachers)
                    }
            }
            else -> {
                return db.from(SubjectEntity)
                    .select()
                    .where { SubjectEntity.uniqueSubjectId eq parameterName }
                    .map {
                        val subjectName = it[SubjectEntity.subjectName] ?: emptyString
                        val uniqueSubjectId = it[SubjectEntity.uniqueSubjectId] ?: emptyString
                        val teachers = it[SubjectEntity.teachers] ?: emptyString
                        val classes = it[SubjectEntity.classes] ?: emptyString
                        SubjectResponse(subjectName, uniqueSubjectId, classes, teachers)
                    }
            }
        }
    }

    override suspend fun updateSubject(uniqueSubjectId: String, updatedSubject: SubjectRequest): SubjectResponse? {
        val rowsAffected = db.update(SubjectEntity){
            set(it.subjectName, updatedSubject.subjectName)
            where { it.uniqueSubjectId eq uniqueSubjectId }
        }
        return if (rowsAffected < 1){
            null
        }else {
            db.from(SubjectEntity)
                .select()
                .where { SubjectEntity.uniqueSubjectId eq uniqueSubjectId }
                .map {
                    val subjectName = it[SubjectEntity.subjectName] ?: emptyString
                    val teachers = it[SubjectEntity.teachers] ?: emptyString
                    val classes = it[SubjectEntity.classes] ?: emptyString
                    SubjectResponse(subjectName, uniqueSubjectId, classes, teachers)
                }.firstOrNull()
        }
    }

    override suspend fun deleteSubject(uniqueSubjectId: String): Int {
        return db.delete(SubjectEntity) {
            SubjectEntity.uniqueSubjectId eq uniqueSubjectId
        }
    }

}