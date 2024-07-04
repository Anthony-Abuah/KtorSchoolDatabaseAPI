package com.dedee.service.helper.teacher_student

import com.dedee.model.helper_tables.TeacherStudent
import com.dedee.model.student.StudentInfo
import com.dedee.model.teacher.TeacherInfo

interface TeacherStudentService {

    suspend fun getAllTeacherStudents(): List<TeacherStudent>?

    suspend fun getAllTeachersOfThisStudent(uniqueStudentId: String): List<TeacherInfo>?

    suspend fun getAllStudentsOfThisTeacher(uniqueTeacherId: String): List<StudentInfo>?

    suspend fun addTeacherStudent(teacherStudent: TeacherStudent): TeacherStudent?

    suspend fun updateTeacherStudent(uniqueTeacherId: String, uniqueStudentId: String, teacherStudent: TeacherStudent): TeacherStudent?

    suspend fun deleteTeacherStudent(teacherStudent: TeacherStudent): Int?

}