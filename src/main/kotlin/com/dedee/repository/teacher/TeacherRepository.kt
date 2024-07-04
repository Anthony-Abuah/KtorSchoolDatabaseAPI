package com.dedee.repository.teacher

import com.dedee.model.teacher.TeacherRequest
import com.dedee.model.teacher.TeacherResponse
import util.ResponseFeedback


interface TeacherRepository {
    suspend fun getAllTeachers(): ResponseFeedback<List<TeacherResponse>>

    suspend fun registerTeacher(teacher: TeacherRequest): ResponseFeedback<TeacherResponse?>

    suspend fun getTeacherByEmail(email: String): ResponseFeedback<TeacherResponse>

    suspend fun getTeacherByUsername(teacherUsername: String): ResponseFeedback<TeacherResponse>

    suspend fun deleteTeacher(uniqueTeacherId: String): ResponseFeedback<String>

    suspend fun updateTeacher(uniqueTeacherId: String, updatedTeacher: TeacherRequest): ResponseFeedback<TeacherResponse>

    suspend fun changeTeacherPassword(uniqueTeacherId: String, currentPassword: String, updatedPassword: String): ResponseFeedback<TeacherResponse>
    
}



