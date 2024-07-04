package com.dedee.repository.helper.teacher_student

import com.dedee.model.helper_tables.TeacherStudent
import com.dedee.model.student.StudentInfo
import com.dedee.model.teacher.TeacherInfo
import util.ResponseFeedback


interface TeacherStudentRepository {
    suspend fun getAllTeacherStudents(): ResponseFeedback<List<TeacherStudent>?>

    suspend fun getAllTeachersOfThisStudent(uniqueStudentId: String): ResponseFeedback<List<TeacherInfo>?>

    suspend fun getAllStudentsOfThisTeacher(uniqueTeacherId: String): ResponseFeedback<List<StudentInfo>?>

    suspend fun addTeacherStudent(teacherStudent: TeacherStudent): ResponseFeedback<TeacherStudent?>

    suspend fun updateTeacherStudent(uniqueTeacherId: String, uniqueStudentId: String, teacherStudent: TeacherStudent): ResponseFeedback<TeacherStudent?>

    suspend fun deleteTeacherStudent(teacherStudent: TeacherStudent): ResponseFeedback<String>

    
}



