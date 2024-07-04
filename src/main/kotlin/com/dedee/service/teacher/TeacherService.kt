package com.dedee.service.teacher

import com.dedee.model.teacher.TeacherRequest
import com.dedee.model.teacher.TeacherResponse

interface TeacherService {

    suspend fun getAllTeachers(): List<TeacherResponse>

    suspend fun registerTeacher(teacher: TeacherRequest, uniqueTeacherId: String, hashedPassword: String, salt: String): Int

    suspend fun findTeacherByUniqueTeacherId(uniqueTeacherId: String): TeacherResponse?

    suspend fun addSubjects(subjects: String, uniqueTeacherId: String)

    suspend fun addStudents(students: String, uniqueTeacherId: String)

    suspend fun addClasses(classes: String, uniqueTeacherId: String)

    suspend fun findTeacherByEmail(email: String): TeacherResponse?

    suspend fun findTeacherByTeacherUserName(teacherUsername: String): TeacherResponse?

    suspend fun updateTeacher(uniqueTeacherId: String, updatedTeacher: TeacherRequest): TeacherResponse?

    suspend fun deleteTeacher(uniqueTeacherId: String): Int?

    suspend fun changeTeacherPassword(uniqueTeacherId: String, updatedPassword: String, updatedSalt: String): TeacherResponse?

    suspend fun passwordIsNotValid(password: String, firstName: String, lastName: String): Boolean
}