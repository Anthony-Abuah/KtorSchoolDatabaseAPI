package com.dedee.service.student

import com.dedee.entities.StudentEntity
import com.dedee.model.grading.GradingInfo
import com.dedee.model.student.StudentRequest
import com.dedee.model.student.StudentResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString
import util.DatabaseEntityFields.Email
import util.DatabaseEntityFields.FirstName
import util.DatabaseEntityFields.Gender
import util.DatabaseEntityFields.LastName
import util.DatabaseEntityFields.OtherNames
import util.DatabaseEntityFields.Photo
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueStudentId

class StudentServiceImpl(
    private val db: Database,
    private val gson: Gson,
) : StudentService
{
    override suspend fun getAllStudents(): List<StudentResponse>? {
        val students = db.from(StudentEntity).select()
            .map {
                val firstName = it[StudentEntity.firstName] ?: emptyString
                val lastName = it[StudentEntity.lastName] ?: emptyString
                val otherNames = it[StudentEntity.otherNames] ?: emptyString
                val uniqueStudentId = it[StudentEntity.uniqueStudentId] ?: emptyString
                val uniqueClassId = it[StudentEntity.uniqueClassId] ?: emptyString
                val dateOfBirth = it[StudentEntity.dateOfBirth] ?: 0
                val email = it[StudentEntity.email] ?: emptyString
                val gender = it[StudentEntity.gender] ?: emptyString
                val photo = it[StudentEntity.photo] ?: emptyString
                val classes = it[StudentEntity.classes] ?: emptyString
                val parents = it[StudentEntity.subjects] ?: emptyString
                val conducts = it[StudentEntity.conducts] ?: emptyString
                val teachers = it[StudentEntity.teachers] ?: emptyString
                val gradings = it[StudentEntity.gradings] ?: emptyString
                val subjects = it[StudentEntity.subjects] ?: emptyString
                val isEnrolled = it[StudentEntity.isEnrolled]
                StudentResponse(firstName, lastName, otherNames, uniqueStudentId, uniqueClassId, dateOfBirth, email, gender,photo,isEnrolled, parents, teachers, subjects, classes, gradings, conducts)
            }
        return students.ifEmpty { null }
    }

    override suspend fun addConduct(conduct: String, uniqueStudentId: String) {
        db.update(StudentEntity){
            set(it.conducts, conduct)
            where { it.uniqueStudentId eq uniqueStudentId }
        }
    }

    override suspend fun registerStudent(student: StudentRequest, uniqueStudentId: String, teachers: String, subjects: String): Int {
        return db.insert(StudentEntity){
            set(it.firstName, student.firstName)
            set(it.lastName, student.lastName)
            set(it.otherNames, student.otherNames)
            set(it.uniqueStudentId, uniqueStudentId)
            set(it.uniqueClassId, student.uniqueClassId)
            set(it.email, student.email)
            set(it.dateOfBirth, student.dateOfBirth)
            set(it.gender, student.gender)
            set(it.photo, student.photo)
        }
    }

    override suspend fun getStudents(parameterName: String, parameterType: String): List<StudentResponse> {
        when(parameterType){
            FirstName -> {
                return db.from(StudentEntity)
                    .select()
                    .where{ StudentEntity.firstName eq parameterName}
                    .map {
                        val firstName = it[StudentEntity.firstName] ?: emptyString
                        val lastName = it[StudentEntity.lastName] ?: emptyString
                        val otherNames = it[StudentEntity.otherNames] ?: emptyString
                        val uniqueStudentId = it[StudentEntity.uniqueStudentId] ?: emptyString
                        val uniqueClassId = it[StudentEntity.uniqueClassId] ?: emptyString
                        val dateOfBirth = it[StudentEntity.dateOfBirth] ?: 0
                        val email = it[StudentEntity.email] ?: emptyString
                        val gender = it[StudentEntity.gender] ?: emptyString
                        val photo = it[StudentEntity.photo] ?: emptyString
                        val classes = it[StudentEntity.classes] ?: emptyString
                        val parents = it[StudentEntity.subjects] ?: emptyString
                        val conducts = it[StudentEntity.conducts] ?: emptyString
                        val teachers = it[StudentEntity.teachers] ?: emptyString
                        val gradings = it[StudentEntity.gradings] ?: emptyString
                        val subjects = it[StudentEntity.subjects] ?: emptyString
                        val isEnrolled = it[StudentEntity.isEnrolled]
                        StudentResponse(firstName, lastName, otherNames, uniqueStudentId, uniqueClassId, dateOfBirth, email, gender,photo,isEnrolled, parents, teachers, subjects, classes, gradings, conducts)
                    }
            }
            LastName -> {
                return db.from(StudentEntity)
                    .select()
                    .where{ StudentEntity.lastName eq parameterName}
                    .map {
                        val firstName = it[StudentEntity.firstName] ?: emptyString
                        val lastName = it[StudentEntity.lastName] ?: emptyString
                        val otherNames = it[StudentEntity.otherNames] ?: emptyString
                        val uniqueStudentId = it[StudentEntity.uniqueStudentId] ?: emptyString
                        val uniqueClassId = it[StudentEntity.uniqueClassId] ?: emptyString
                        val dateOfBirth = it[StudentEntity.dateOfBirth] ?: 0
                        val email = it[StudentEntity.email] ?: emptyString
                        val gender = it[StudentEntity.gender] ?: emptyString
                        val photo = it[StudentEntity.photo] ?: emptyString
                        val classes = it[StudentEntity.classes] ?: emptyString
                        val parents = it[StudentEntity.subjects] ?: emptyString
                        val conducts = it[StudentEntity.conducts] ?: emptyString
                        val teachers = it[StudentEntity.teachers] ?: emptyString
                        val gradings = it[StudentEntity.gradings] ?: emptyString
                        val subjects = it[StudentEntity.subjects] ?: emptyString
                        val isEnrolled = it[StudentEntity.isEnrolled]
                        StudentResponse(firstName, lastName, otherNames, uniqueStudentId, uniqueClassId, dateOfBirth, email, gender,photo,isEnrolled, parents, teachers, subjects, classes, gradings, conducts)
                    }
            }
            OtherNames -> {
                return db.from(StudentEntity)
                    .select()
                    .where{ StudentEntity.otherNames eq parameterName}
                    .map {
                        val firstName = it[StudentEntity.firstName] ?: emptyString
                        val lastName = it[StudentEntity.lastName] ?: emptyString
                        val otherNames = it[StudentEntity.otherNames] ?: emptyString
                        val uniqueStudentId = it[StudentEntity.uniqueStudentId] ?: emptyString
                        val uniqueClassId = it[StudentEntity.uniqueClassId] ?: emptyString
                        val dateOfBirth = it[StudentEntity.dateOfBirth] ?: 0
                        val email = it[StudentEntity.email] ?: emptyString
                        val gender = it[StudentEntity.gender] ?: emptyString
                        val photo = it[StudentEntity.photo] ?: emptyString
                        val classes = it[StudentEntity.classes] ?: emptyString
                        val parents = it[StudentEntity.subjects] ?: emptyString
                        val conducts = it[StudentEntity.conducts] ?: emptyString
                        val teachers = it[StudentEntity.teachers] ?: emptyString
                        val gradings = it[StudentEntity.gradings] ?: emptyString
                        val subjects = it[StudentEntity.subjects] ?: emptyString
                        val isEnrolled = it[StudentEntity.isEnrolled]
                        StudentResponse(firstName, lastName, otherNames, uniqueStudentId, uniqueClassId, dateOfBirth, email, gender,photo,isEnrolled, parents, teachers, subjects, classes, gradings, conducts)
                    }
            }
            Photo -> {
                return db.from(StudentEntity)
                    .select()
                    .where{ StudentEntity.photo eq parameterName}
                    .map {
                        val firstName = it[StudentEntity.firstName] ?: emptyString
                        val lastName = it[StudentEntity.lastName] ?: emptyString
                        val otherNames = it[StudentEntity.otherNames] ?: emptyString
                        val uniqueStudentId = it[StudentEntity.uniqueStudentId] ?: emptyString
                        val uniqueClassId = it[StudentEntity.uniqueClassId] ?: emptyString
                        val dateOfBirth = it[StudentEntity.dateOfBirth] ?: 0
                        val email = it[StudentEntity.email] ?: emptyString
                        val gender = it[StudentEntity.gender] ?: emptyString
                        val photo = it[StudentEntity.photo] ?: emptyString
                        val classes = it[StudentEntity.classes] ?: emptyString
                        val parents = it[StudentEntity.subjects] ?: emptyString
                        val conducts = it[StudentEntity.conducts] ?: emptyString
                        val teachers = it[StudentEntity.teachers] ?: emptyString
                        val gradings = it[StudentEntity.gradings] ?: emptyString
                        val subjects = it[StudentEntity.subjects] ?: emptyString
                        val isEnrolled = it[StudentEntity.isEnrolled]
                        StudentResponse(firstName, lastName, otherNames, uniqueStudentId, uniqueClassId, dateOfBirth, email, gender,photo,isEnrolled, parents, teachers, subjects, classes, gradings, conducts)
                    }
            }
            UniqueClassId -> {
                return db.from(StudentEntity)
                    .select()
                    .where{ StudentEntity.uniqueClassId eq parameterName}
                    .map {
                        val firstName = it[StudentEntity.firstName] ?: emptyString
                        val lastName = it[StudentEntity.lastName] ?: emptyString
                        val otherNames = it[StudentEntity.otherNames] ?: emptyString
                        val uniqueStudentId = it[StudentEntity.uniqueStudentId] ?: emptyString
                        val uniqueClassId = it[StudentEntity.uniqueClassId] ?: emptyString
                        val dateOfBirth = it[StudentEntity.dateOfBirth] ?: 0
                        val email = it[StudentEntity.email] ?: emptyString
                        val gender = it[StudentEntity.gender] ?: emptyString
                        val photo = it[StudentEntity.photo] ?: emptyString
                        val classes = it[StudentEntity.classes] ?: emptyString
                        val parents = it[StudentEntity.subjects] ?: emptyString
                        val conducts = it[StudentEntity.conducts] ?: emptyString
                        val teachers = it[StudentEntity.teachers] ?: emptyString
                        val gradings = it[StudentEntity.gradings] ?: emptyString
                        val subjects = it[StudentEntity.subjects] ?: emptyString
                        val isEnrolled = it[StudentEntity.isEnrolled]
                        StudentResponse(firstName, lastName, otherNames, uniqueStudentId, uniqueClassId, dateOfBirth, email, gender,photo,isEnrolled, parents, teachers, subjects, classes, gradings, conducts)
                    }
            }
            Gender -> {
                return db.from(StudentEntity)
                    .select()
                    .where{ StudentEntity.gender eq parameterName}
                    .map {
                        val firstName = it[StudentEntity.firstName] ?: emptyString
                        val lastName = it[StudentEntity.lastName] ?: emptyString
                        val otherNames = it[StudentEntity.otherNames] ?: emptyString
                        val uniqueStudentId = it[StudentEntity.uniqueStudentId] ?: emptyString
                        val uniqueClassId = it[StudentEntity.uniqueClassId] ?: emptyString
                        val dateOfBirth = it[StudentEntity.dateOfBirth] ?: 0
                        val email = it[StudentEntity.email] ?: emptyString
                        val gender = it[StudentEntity.gender] ?: emptyString
                        val photo = it[StudentEntity.photo] ?: emptyString
                        val classes = it[StudentEntity.classes] ?: emptyString
                        val parents = it[StudentEntity.subjects] ?: emptyString
                        val conducts = it[StudentEntity.conducts] ?: emptyString
                        val teachers = it[StudentEntity.teachers] ?: emptyString
                        val gradings = it[StudentEntity.gradings] ?: emptyString
                        val subjects = it[StudentEntity.subjects] ?: emptyString
                        val isEnrolled = it[StudentEntity.isEnrolled]
                        StudentResponse(firstName, lastName, otherNames, uniqueStudentId, uniqueClassId, dateOfBirth, email, gender,photo,isEnrolled, parents, teachers, subjects, classes, gradings, conducts)
                    }
            }
            Email -> {
                return db.from(StudentEntity)
                    .select()
                    .where{ StudentEntity.email eq parameterName}
                    .map {
                        val firstName = it[StudentEntity.firstName] ?: emptyString
                        val lastName = it[StudentEntity.lastName] ?: emptyString
                        val otherNames = it[StudentEntity.otherNames] ?: emptyString
                        val uniqueStudentId = it[StudentEntity.uniqueStudentId] ?: emptyString
                        val uniqueClassId = it[StudentEntity.uniqueClassId] ?: emptyString
                        val dateOfBirth = it[StudentEntity.dateOfBirth] ?: 0
                        val email = it[StudentEntity.email] ?: emptyString
                        val gender = it[StudentEntity.gender] ?: emptyString
                        val photo = it[StudentEntity.photo] ?: emptyString
                        val classes = it[StudentEntity.classes] ?: emptyString
                        val parents = it[StudentEntity.subjects] ?: emptyString
                        val conducts = it[StudentEntity.conducts] ?: emptyString
                        val teachers = it[StudentEntity.teachers] ?: emptyString
                        val gradings = it[StudentEntity.gradings] ?: emptyString
                        val subjects = it[StudentEntity.subjects] ?: emptyString
                        val isEnrolled = it[StudentEntity.isEnrolled]
                        StudentResponse(firstName, lastName, otherNames, uniqueStudentId, uniqueClassId, dateOfBirth, email, gender,photo,isEnrolled, parents, teachers, subjects, classes, gradings, conducts)
                    }
            }
            else -> {
                return db.from(StudentEntity)
                    .select()
                    .where{ StudentEntity.uniqueStudentId eq parameterName}
                    .map {
                        val firstName = it[StudentEntity.firstName] ?: emptyString
                        val lastName = it[StudentEntity.lastName] ?: emptyString
                        val otherNames = it[StudentEntity.otherNames] ?: emptyString
                        val uniqueStudentId = it[StudentEntity.uniqueStudentId] ?: emptyString
                        val uniqueClassId = it[StudentEntity.uniqueClassId] ?: emptyString
                        val dateOfBirth = it[StudentEntity.dateOfBirth] ?: 0
                        val email = it[StudentEntity.email] ?: emptyString
                        val gender = it[StudentEntity.gender] ?: emptyString
                        val photo = it[StudentEntity.photo] ?: emptyString
                        val classes = it[StudentEntity.classes] ?: emptyString
                        val parents = it[StudentEntity.subjects] ?: emptyString
                        val conducts = it[StudentEntity.conducts] ?: emptyString
                        val teachers = it[StudentEntity.teachers] ?: emptyString
                        val gradings = it[StudentEntity.gradings] ?: emptyString
                        val subjects = it[StudentEntity.subjects] ?: emptyString
                        val isEnrolled = it[StudentEntity.isEnrolled]
                        StudentResponse(firstName, lastName, otherNames, uniqueStudentId, uniqueClassId, dateOfBirth, email, gender,photo,isEnrolled, parents, teachers, subjects, classes, gradings, conducts)
                    }
            }
        }
    }

    override suspend fun updateStudent(uniqueStudentId: String, updatedStudent: StudentRequest): StudentResponse? {

        val rowsAffected = db.update(StudentEntity){
            set(it.firstName, updatedStudent.firstName)
            set(it.lastName, updatedStudent.lastName)
            set(it.otherNames, updatedStudent.otherNames)
            set(it.uniqueClassId, updatedStudent.uniqueClassId)
            set(it.email, updatedStudent.email)
            set(it.dateOfBirth, updatedStudent.dateOfBirth)
            set(it.gender, updatedStudent.gender)
            set(it.photo, updatedStudent.photo)
            where { it.uniqueStudentId eq uniqueStudentId }
        }
        return if (rowsAffected < 1){
            null
        }else {
            db.from(StudentEntity)
                .select()
                .where { StudentEntity.uniqueStudentId eq uniqueStudentId }
                .map {
                    val firstName = it[StudentEntity.firstName] ?: emptyString
                    val lastName = it[StudentEntity.lastName] ?: emptyString
                    val otherNames = it[StudentEntity.otherNames] ?: emptyString
                    val uniqueClassId = it[StudentEntity.uniqueClassId] ?: emptyString
                    val dateOfBirth = it[StudentEntity.dateOfBirth] ?: 0
                    val email = it[StudentEntity.email] ?: emptyString
                    val gender = it[StudentEntity.gender] ?: emptyString
                    val photo = it[StudentEntity.photo] ?: emptyString
                    val classes = it[StudentEntity.classes] ?: emptyString
                    val parents = it[StudentEntity.subjects] ?: emptyString
                    val conducts = it[StudentEntity.conducts] ?: emptyString
                    val teachers = it[StudentEntity.teachers] ?: emptyString
                    val subjects = it[StudentEntity.subjects] ?: emptyString
                    val gradings = it[StudentEntity.gradings] ?: emptyString
                    val isEnrolled = it[StudentEntity.isEnrolled]
                    StudentResponse(firstName, lastName, otherNames, uniqueStudentId, uniqueClassId, dateOfBirth, email, gender,photo,isEnrolled, parents, teachers, subjects, classes, gradings, conducts)
                }.firstOrNull()
        }

    }

    override suspend fun addParents(parents: String, uniqueStudentId: String) {
        db.update(StudentEntity){
            set(it.parents, parents)
            where { it.uniqueStudentId eq uniqueStudentId }
        }
    }

    override suspend fun addTeachers(teachers: String, uniqueStudentId: String) {
        db.update(StudentEntity){
            set(it.teachers, teachers)
            where { it.uniqueStudentId eq uniqueStudentId }
        }
    }

    override suspend fun addSubjects(subjects: String, uniqueStudentId: String) {
        db.update(StudentEntity){
            set(it.subjects, subjects)
            where { it.uniqueStudentId eq uniqueStudentId }
        }
    }

    override suspend fun addGradings(gradings: String, uniqueStudentId: String): List<GradingInfo>? {
        val updatedRows = db.update(StudentEntity){
            set(it.gradings, gradings)
            where { it.uniqueStudentId eq uniqueStudentId }
        }
        return if (updatedRows < 1){
            null
        } else {
            val student = getStudents(uniqueStudentId, UniqueStudentId).firstOrNull()
            val thisGradings = student?.gradings
            val listOfGradingInfoType = object : TypeToken<List<GradingInfo>>() {}.type
            gson.fromJson(thisGradings, listOfGradingInfoType)
        }
    }

    override suspend fun deleteStudent(uniqueStudentId: String): Int {
        return db.delete(StudentEntity) {
            StudentEntity.uniqueStudentId eq uniqueStudentId
        }
    }
}