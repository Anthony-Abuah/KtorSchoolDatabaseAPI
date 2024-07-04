package com.dedee.service.school_class

import com.dedee.entities.SchoolClassEntity
import com.dedee.model.school_class.SchoolClassRequest
import com.dedee.model.school_class.SchoolClassResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString
import util.DatabaseEntityFields.ClassName
import util.DatabaseEntityFields.ClassStage

class SchoolClassServiceImpl(
    private val db: Database,
) : SchoolClassService{
    override suspend fun getAllSchoolClasses(): List<SchoolClassResponse>? {
        val schoolClass = db.from(SchoolClassEntity).select()
            .map {
                val className = it[SchoolClassEntity.className] ?: emptyString
                val uniqueClassId = it[SchoolClassEntity.uniqueClassId] ?: emptyString
                val classStage = it[SchoolClassEntity.classStage] ?: emptyString
                val academicYear = it[SchoolClassEntity.academicYear] ?: emptyString
                val numberOfBoys = it[SchoolClassEntity.numberOfBoys] ?: 0
                val numberOfGirls = it[SchoolClassEntity.numberOfGirls] ?: 0
                val totalNumberOfStudents = it[SchoolClassEntity.totalNumberOfStudents] ?: 0
                val teachers = it[SchoolClassEntity.teachers] ?: emptyString
                val students = it[SchoolClassEntity.students] ?: emptyString
                val subjects = it[SchoolClassEntity.subjects] ?: emptyString
                SchoolClassResponse(className, uniqueClassId, classStage, academicYear, numberOfBoys, numberOfGirls, totalNumberOfStudents, teachers, subjects, students)
            }
        return schoolClass.ifEmpty { null }
    }

    override suspend fun addSubjects(subjects: String, uniqueClassId: String) {
        db.update(SchoolClassEntity){
            set(it.subjects, subjects)
            where { it.uniqueClassId eq uniqueClassId }
        }
    }

    override suspend fun addTeachers(teachers: String, uniqueClassId: String) {
        db.update(SchoolClassEntity){
            set(it.teachers, teachers)
            where { it.uniqueClassId eq uniqueClassId }
        }
    }

    override suspend fun addSchoolClass(schoolClass: SchoolClassRequest, uniqueClassId: String): Int {
        return db.insert(SchoolClassEntity){
            set(it.className, schoolClass.className)
            set(it.classStage, schoolClass.classStage)
            set(it.uniqueClassId, uniqueClassId)
            set(it.academicYear, schoolClass.academicYear)
            set(it.numberOfGirls, schoolClass.numberOfGirls)
            set(it.numberOfBoys, schoolClass.numberOfBoys)
            set(it.totalNumberOfStudents, schoolClass.totalNumberOfStudents)
        }
    }

    override suspend fun getSchoolClass(parameterName: String, parameterType: String): List<SchoolClassResponse> {
        when(parameterType){
            ClassName -> {
                return db.from(SchoolClassEntity)
                    .select()
                    .where{ SchoolClassEntity.className eq parameterName}
                    .map {
                        val className = it[SchoolClassEntity.className] ?: emptyString
                        val academicYear = it[SchoolClassEntity.academicYear] ?: emptyString
                        val uniqueClassId = it[SchoolClassEntity.uniqueClassId] ?: emptyString
                        val classStage = it[SchoolClassEntity.classStage] ?: emptyString
                        val numberOfBoys = it[SchoolClassEntity.numberOfBoys] ?: 0
                        val numberOfGirls = it[SchoolClassEntity.numberOfGirls] ?: 0
                        val totalNumberOfStudents = it[SchoolClassEntity.totalNumberOfStudents] ?: 0
                        val teachers = it[SchoolClassEntity.teachers] ?: emptyString
                        val students = it[SchoolClassEntity.students] ?: emptyString
                        val subjects = it[SchoolClassEntity.subjects] ?: emptyString
                        SchoolClassResponse(className, uniqueClassId, classStage, academicYear, numberOfBoys, numberOfGirls, totalNumberOfStudents, teachers, subjects, students)
                    }
            }
            ClassStage -> {
                return db.from(SchoolClassEntity)
                    .select()
                    .where{ SchoolClassEntity.classStage eq parameterName}
                    .map {
                        val className = it[SchoolClassEntity.className] ?: emptyString
                        val academicYear = it[SchoolClassEntity.academicYear] ?: emptyString
                        val uniqueClassId = it[SchoolClassEntity.uniqueClassId] ?: emptyString
                        val classStage = it[SchoolClassEntity.classStage] ?: emptyString
                        val numberOfBoys = it[SchoolClassEntity.numberOfBoys] ?: 0
                        val numberOfGirls = it[SchoolClassEntity.numberOfGirls] ?: 0
                        val totalNumberOfStudents = it[SchoolClassEntity.totalNumberOfStudents] ?: 0
                        val teachers = it[SchoolClassEntity.teachers] ?: emptyString
                        val students = it[SchoolClassEntity.students] ?: emptyString
                        val subjects = it[SchoolClassEntity.subjects] ?: emptyString
                        SchoolClassResponse(className, uniqueClassId, classStage, academicYear, numberOfBoys, numberOfGirls, totalNumberOfStudents, teachers, subjects, students)
                    }
            }
            else -> {
                return db.from(SchoolClassEntity)
                    .select()
                    .where { SchoolClassEntity.uniqueClassId eq parameterName }
                    .map {
                        val className = it[SchoolClassEntity.className] ?: emptyString
                        val academicYear = it[SchoolClassEntity.academicYear] ?: emptyString
                        val uniqueClassId = it[SchoolClassEntity.uniqueClassId] ?: emptyString
                        val classStage = it[SchoolClassEntity.classStage] ?: emptyString
                        val numberOfBoys = it[SchoolClassEntity.numberOfBoys] ?: 0
                        val numberOfGirls = it[SchoolClassEntity.numberOfGirls] ?: 0
                        val totalNumberOfStudents = it[SchoolClassEntity.totalNumberOfStudents] ?: 0
                        val teachers = it[SchoolClassEntity.teachers] ?: emptyString
                        val students = it[SchoolClassEntity.students] ?: emptyString
                        val subjects = it[SchoolClassEntity.subjects] ?: emptyString
                        SchoolClassResponse(className, uniqueClassId, classStage, academicYear, numberOfBoys, numberOfGirls, totalNumberOfStudents, teachers, subjects, students)
                    }
            }
        }
    }

    override suspend fun updateSchoolClass(uniqueClassId: String, updatedClass: SchoolClassRequest): SchoolClassResponse? {
        val rowsAffected = db.update(SchoolClassEntity){
            set(it.className, updatedClass.className)
            set(it.academicYear, updatedClass.academicYear)
            set(it.classStage, updatedClass.classStage)
            set(it.numberOfGirls, updatedClass.numberOfGirls)
            set(it.numberOfBoys, updatedClass.numberOfBoys)
            set(it.totalNumberOfStudents, updatedClass.totalNumberOfStudents)
            where { it.uniqueClassId eq uniqueClassId }
        }
        return if (rowsAffected < 1){
            null
        }else {
            db.from(SchoolClassEntity)
                .select()
                .where { SchoolClassEntity.uniqueClassId eq uniqueClassId }
                .map {
                    val className = it[SchoolClassEntity.className] ?: emptyString
                    val academicYear = it[SchoolClassEntity.academicYear] ?: emptyString
                    val classStage = it[SchoolClassEntity.classStage] ?: emptyString
                    val numberOfBoys = it[SchoolClassEntity.numberOfBoys] ?: 0
                    val numberOfGirls = it[SchoolClassEntity.numberOfGirls] ?: 0
                    val totalNumberOfStudents = it[SchoolClassEntity.totalNumberOfStudents] ?: 0
                    val teachers = it[SchoolClassEntity.teachers] ?: emptyString
                    val students = it[SchoolClassEntity.students] ?: emptyString
                    val subjects = it[SchoolClassEntity.subjects] ?: emptyString
                    SchoolClassResponse(className, uniqueClassId, classStage, academicYear, numberOfBoys, numberOfGirls, totalNumberOfStudents, teachers, subjects, students)
                }.firstOrNull()
        }
    }

    override suspend fun deleteSchoolClass(uniqueClassId: String): Int {
        return db.delete(SchoolClassEntity) {
            SchoolClassEntity.uniqueClassId eq uniqueClassId
        }
    }

}