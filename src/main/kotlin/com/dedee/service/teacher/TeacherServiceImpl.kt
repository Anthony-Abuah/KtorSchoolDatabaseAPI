package com.dedee.service.teacher

import com.dedee.entities.TeacherEntity
import com.dedee.model.teacher.TeacherRequest
import com.dedee.model.teacher.TeacherResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString

class TeacherServiceImpl(
    private val db: Database,
) : TeacherService {
    override suspend fun getAllTeachers(): List<TeacherResponse> {
        val teachers = db.from(TeacherEntity).select()
            .map {
                val firstName = it[TeacherEntity.firstName] ?: emptyString
                val lastName = it[TeacherEntity.lastName] ?: emptyString
                val otherNames = it[TeacherEntity.otherNames] ?: emptyString
                val teacherUsername = it[TeacherEntity.teacherUsername] ?: emptyString
                val uniqueTeacherId = it[TeacherEntity.uniqueTeacherId] ?: emptyString
                val password = it[TeacherEntity.password] ?: emptyString
                val email = it[TeacherEntity.email] ?: emptyString
                val salt = it[TeacherEntity.salt] ?: emptyString
                val gender = it[TeacherEntity.gender] ?: emptyString
                val contact = it[TeacherEntity.contact] ?: emptyString
                val classes = it[TeacherEntity.classes] ?: emptyString
                val subjects = it[TeacherEntity.subjects] ?: emptyString
                val students = it[TeacherEntity.students] ?: emptyString
                val isStillAtThisSchool = it[TeacherEntity.isStillAtThisSchool]
                TeacherResponse(firstName, lastName, otherNames, teacherUsername, uniqueTeacherId, password, salt, email, contact, gender, isStillAtThisSchool, subjects, classes,students)
            }
        return teachers
    }

    override suspend fun registerTeacher(teacher: TeacherRequest, uniqueTeacherId: String, hashedPassword: String, salt: String): Int {
        return db.insert(TeacherEntity){
            set(it.firstName, teacher.firstName)
            set(it.lastName, teacher.lastName)
            set(it.otherNames, teacher.otherNames)
            set(it.teacherUsername, teacher.teacherUsername)
            set(it.uniqueTeacherId, uniqueTeacherId)
            set(it.email, teacher.email)
            set(it.password, hashedPassword)
            set(it.salt, salt)
            set(it.gender, teacher.gender)
            set(it.contact, teacher.contact)
        }
    }

    override suspend fun findTeacherByUniqueTeacherId(uniqueTeacherId: String): TeacherResponse? {
        return db.from(TeacherEntity)
            .select()
            .where{TeacherEntity.uniqueTeacherId eq uniqueTeacherId}
            .map {
                val firstName = it[TeacherEntity.firstName] ?: emptyString
                val lastName = it[TeacherEntity.lastName] ?: emptyString
                val otherNames = it[TeacherEntity.otherNames] ?: emptyString
                val teacherUsername = it[TeacherEntity.teacherUsername] ?: emptyString
                val password = it[TeacherEntity.password] ?: emptyString
                val salt = it[TeacherEntity.salt] ?: emptyString
                val email = it[TeacherEntity.email] ?: emptyString
                val gender = it[TeacherEntity.gender] ?: emptyString
                val contact = it[TeacherEntity.contact] ?: emptyString
                val classes = it[TeacherEntity.classes] ?: emptyString
                val subjects = it[TeacherEntity.subjects] ?: emptyString
                val students = it[TeacherEntity.students] ?: emptyString
                val isStillAtThisSchool = it[TeacherEntity.isStillAtThisSchool]
                TeacherResponse(firstName, lastName, otherNames, teacherUsername, uniqueTeacherId, password, salt, email, contact, gender, isStillAtThisSchool, subjects, classes,students)
            }.firstOrNull()
    }

    override suspend fun addSubjects(subjects: String, uniqueTeacherId: String) {
        db.update(TeacherEntity){
            set(it.subjects, subjects)
            where { it.uniqueTeacherId eq uniqueTeacherId }
        }
    }

    override suspend fun addStudents(students: String, uniqueTeacherId: String) {
        db.update(TeacherEntity){
            set(it.students, students)
            where { it.uniqueTeacherId eq uniqueTeacherId }
        }
    }

    override suspend fun addClasses(classes: String, uniqueTeacherId: String) {
        db.update(TeacherEntity){
            set(it.classes, classes)
            where { it.uniqueTeacherId eq uniqueTeacherId }
        }
    }

    override suspend fun findTeacherByEmail(email: String): TeacherResponse? {
        return db.from(TeacherEntity)
            .select()
            .where{TeacherEntity.email eq email}
            .map {
                val firstName = it[TeacherEntity.firstName] ?: emptyString
                val lastName = it[TeacherEntity.lastName] ?: emptyString
                val otherNames = it[TeacherEntity.otherNames] ?: emptyString
                val teacherUsername = it[TeacherEntity.teacherUsername] ?: emptyString
                val uniqueTeacherId = it[TeacherEntity.uniqueTeacherId] ?: emptyString
                val password = it[TeacherEntity.password] ?: emptyString
                val salt = it[TeacherEntity.salt] ?: emptyString
                val gender = it[TeacherEntity.gender] ?: emptyString
                val contact = it[TeacherEntity.contact] ?: emptyString
                val classes = it[TeacherEntity.classes] ?: emptyString
                val subjects = it[TeacherEntity.subjects] ?: emptyString
                val students = it[TeacherEntity.students] ?: emptyString
                val isStillAtThisSchool = it[TeacherEntity.isStillAtThisSchool]
                TeacherResponse(firstName, lastName, otherNames, teacherUsername, uniqueTeacherId, password, salt, email, contact, gender, isStillAtThisSchool, subjects, classes,students)
            }.firstOrNull()
    }

    override suspend fun findTeacherByTeacherUserName(teacherUsername: String): TeacherResponse? {
        return db.from(TeacherEntity)
            .select()
            .where{TeacherEntity.teacherUsername eq teacherUsername}
            .map {
                val firstName = it[TeacherEntity.firstName] ?: emptyString
                val lastName = it[TeacherEntity.lastName] ?: emptyString
                val otherNames = it[TeacherEntity.otherNames] ?: emptyString
                val uniqueTeacherId = it[TeacherEntity.uniqueTeacherId] ?: emptyString
                val password = it[TeacherEntity.password] ?: emptyString
                val salt = it[TeacherEntity.salt] ?: emptyString
                val email = it[TeacherEntity.email] ?: emptyString
                val gender = it[TeacherEntity.gender] ?: emptyString
                val contact = it[TeacherEntity.contact] ?: emptyString
                val classes = it[TeacherEntity.classes] ?: emptyString
                val subjects = it[TeacherEntity.subjects] ?: emptyString
                val students = it[TeacherEntity.students] ?: emptyString
                val isStillAtThisSchool = it[TeacherEntity.isStillAtThisSchool]
                TeacherResponse(firstName, lastName, otherNames, teacherUsername, uniqueTeacherId, password, salt, email, contact, gender, isStillAtThisSchool, subjects, classes,students)
            }.firstOrNull()
    }

    override suspend fun updateTeacher(uniqueTeacherId: String, updatedTeacher: TeacherRequest): TeacherResponse? {
        val updateResult = db.update(TeacherEntity){
            set(it.firstName, updatedTeacher.firstName)
            set(it.lastName, updatedTeacher.lastName)
            set(it.otherNames, updatedTeacher.otherNames)
            set(it.teacherUsername, updatedTeacher.teacherUsername)
            set(it.email, updatedTeacher.email)
            set(it.gender, updatedTeacher.gender)
            set(it.contact, updatedTeacher.contact)
            where { it.uniqueTeacherId eq uniqueTeacherId }
        }
        return if (updateResult < 1){
            null
        }else {
            db.from(TeacherEntity)
                .select()
                .where { TeacherEntity.uniqueTeacherId eq uniqueTeacherId }
                .map {
                    val firstName = it[TeacherEntity.firstName] ?: emptyString
                    val lastName = it[TeacherEntity.lastName] ?: emptyString
                    val otherNames = it[TeacherEntity.otherNames] ?: emptyString
                    val teacherUsername = it[TeacherEntity.teacherUsername] ?: emptyString
                    val password = it[TeacherEntity.password] ?: emptyString
                    val email = it[TeacherEntity.email] ?: emptyString
                    val salt = it[TeacherEntity.salt] ?: emptyString
                    val gender = it[TeacherEntity.gender] ?: emptyString
                    val contact = it[TeacherEntity.contact] ?: emptyString
                    val classes = it[TeacherEntity.classes] ?: emptyString
                    val subjects = it[TeacherEntity.subjects] ?: emptyString
                    val students = it[TeacherEntity.students] ?: emptyString
                    val isStillAtThisSchool = it[TeacherEntity.isStillAtThisSchool]
                    TeacherResponse(firstName, lastName, otherNames, teacherUsername, uniqueTeacherId, password, salt, email, contact, gender, isStillAtThisSchool, subjects, classes,students)
                }.firstOrNull()
        }

    }

    override suspend fun deleteTeacher(uniqueTeacherId: String): Int {
        return db.delete(TeacherEntity) {
            TeacherEntity.uniqueTeacherId eq uniqueTeacherId
        }
    }

    override suspend fun changeTeacherPassword(
        uniqueTeacherId: String,
        updatedPassword: String,
        updatedSalt: String,
    ): TeacherResponse? {
        val passwordResult = db.update(TeacherEntity){
            set(it.password, updatedPassword)
            set(it.salt, updatedSalt)
            where { it.uniqueTeacherId eq uniqueTeacherId }
        }
        return if (passwordResult < 1){
            null
        }else{
         db.from(TeacherEntity)
            .select()
            .where{ TeacherEntity.uniqueTeacherId eq uniqueTeacherId}
            .map {
                val firstName = it[TeacherEntity.firstName] ?: emptyString
                val lastName = it[TeacherEntity.lastName] ?: emptyString
                val otherNames = it[TeacherEntity.otherNames] ?: emptyString
                val teacherUsername = it[TeacherEntity.teacherUsername] ?: emptyString
                val password = it[TeacherEntity.password] ?: emptyString
                val email = it[TeacherEntity.email] ?: emptyString
                val salt = it[TeacherEntity.salt] ?: emptyString
                val gender = it[TeacherEntity.gender] ?: emptyString
                val contact = it[TeacherEntity.contact] ?: emptyString
                val classes = it[TeacherEntity.classes] ?: emptyString
                val subjects = it[TeacherEntity.subjects] ?: emptyString
                val students = it[TeacherEntity.students] ?: emptyString
                val isStillAtThisSchool = it[TeacherEntity.isStillAtThisSchool]
                TeacherResponse(firstName, lastName, otherNames, teacherUsername, uniqueTeacherId, password, salt, email, contact, gender, isStillAtThisSchool, subjects, classes,students)
            }.firstOrNull()
        }
    }

    override suspend fun passwordIsNotValid(password: String, firstName: String, lastName: String): Boolean {
        val thisFirstName = firstName.lowercase().take(5)
        val thisLastName = lastName.lowercase().take(5)
        val existingPassword = db.from(TeacherEntity)
            .select()
            .where{TeacherEntity.password eq password}
            .map { it[TeacherEntity.password] ?: emptyString
            }.firstOrNull()
        return (password.length < 10 || password.contains(thisFirstName) || password.contains(thisLastName) || (existingPassword != null))
    }

}